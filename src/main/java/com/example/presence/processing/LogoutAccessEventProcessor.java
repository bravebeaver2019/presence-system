package com.example.presence.processing;

import com.example.presence.model.FingerprintScan;
import com.example.presence.model.TimeRange;
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
    TimeRangeRepository repository;

    @Override
    public void processScanEvent(FingerprintScan scan) {
        log.info("LOGOUT scan");
        // a logout event has arrived, now its time to find the latest unclosed login in range table,
        // update the table with the logout date and compute for each day, the number of minutes
        // of presence
        Optional<TimeRange> first = repository.findLastUnclosedRanges(scan.getFingerprintHash()).stream().findFirst();
        if (first.isPresent()) {
            // an open login was found
            TimeRange lastLogin = first.get();
            lastLogin.setLogoutDate(scan.getScanTimestamp());
            repository.save(lastLogin);

            // now we will calculate the time diff in minutes between login and logout events
            log.info(lastLogin.toString());
            log.info("minutes of presence; " + lastLogin.presenceMinutes());
            // if both dates in same day then one single entry
            // if both dates in different day then more than one entry is needed
        } else {
            log.warning("a LOGOUT event with a previous LOGIN event was received");
        }
    }
}