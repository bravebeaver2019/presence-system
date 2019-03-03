package com.example.presence.processing;

import com.example.presence.model.FingerprintScan;
import com.example.presence.model.TimeRange;
import com.example.presence.persistence.TimeRangeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("login")
@Log(topic = "::processing::")
public class LoginAccessEventProcessor implements AccessEventProcessor {

    @Autowired
    TimeRangeRepository repository;

    @Override
    public void processScanEvent(FingerprintScan scan) {
        // upon login we just create and save a new TimeRange entity
        TimeRange timeRange = TimeRange.builder()
                .userId(scan.getFingerprintHash())
                .loginDate(scan.getScanTimestamp()).build();
        repository.save(timeRange);
        log.info("New timeRange entry persisted for login scan");
    }
}
