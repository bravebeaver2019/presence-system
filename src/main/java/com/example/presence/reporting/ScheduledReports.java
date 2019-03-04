package com.example.presence.reporting;

import com.example.presence.common.persistence.DailyPresenceRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log
public class ScheduledReports {

    @Autowired
    DailyPresenceRepository repository;

    @Scheduled(fixedRate = 5000)
    public void reportLastDayPresencePerEmployee() {
        repository.findAll().forEach(o -> log.info(o.toString()));
        log.info(repository.presencePerDayPerEmployee2().keySet().toString());
    }

}
