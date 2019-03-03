package com.example.presence.processing;

import com.example.presence.model.DailyPresence;
import com.example.presence.model.FingerprintScan;
import com.example.presence.model.TimeRange;
import com.example.presence.persistence.DailyPresenceRepository;
import com.example.presence.persistence.TimeRangeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier("logout")
@Log(topic = "::processing::")
public class LogoutAccessEventProcessor implements AccessEventProcessor {

    @Autowired
    TimeRangeRepository timeRangeRepository;
    @Autowired
    DailyPresenceRepository presenceRepository;

    @Override
    public void processScanEvent(FingerprintScan scan) {
        log.info("LOGOUT scan");
        // a logout event has arrived, now its time to find the latest unclosed login in range table,
        // update the table with the logout date and compute for each day, the number of minutes
        // of presence
        Optional<TimeRange> first = timeRangeRepository.findLastUnclosedRanges(scan.getFingerprintHash()).stream().findFirst();
        if (first.isPresent()) {
            // an open login was found
            TimeRange lastLogin = first.get();
            lastLogin.setLogoutDate(scan.getScanTimestamp());
            timeRangeRepository.save(lastLogin);

            if (lastLogin.isSameDay()) {
                log.info("minutes of presence; " + lastLogin.presenceMinutes());
                DailyPresence presence = DailyPresence.builder()
                        .userId(lastLogin.getUserId())
                        .presenceMinutes(lastLogin.presenceMinutes())
                        .yearsSinceEpoch(lastLogin.yearsSinceEpoch())
                        .monthsSinceEpoch(lastLogin.monthsSinceEpoch())
                        .weeksSinceEpoch(lastLogin.weeksSinceEpoch())
                        .daysSinceEpoch(lastLogin.daysSinceEpoch())
                        .build();
                presenceRepository.save(presence);
            } else {
                // todo: create one record per day with the total amount of minutes
            }
        } else {
            log.warning("a LOGOUT event with a previous LOGIN event was received");
        }
    }
}