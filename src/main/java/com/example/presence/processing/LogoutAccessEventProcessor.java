package com.example.presence.processing;

import com.example.presence.model.FingerprintScan;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("logout")
@Log(topic = "::processing::")
public class LogoutAccessEventProcessor implements AccessEventProcessor {
    @Override
    public void processScanEvent(FingerprintScan scan) {
        log.info("LOGOUT scan");
        // a logout event has arrived, now its time to find the latest unclosed login in range table,
        // update the table with the logout date and compute for each day, the number of minutes
        // of presence
    }
}
