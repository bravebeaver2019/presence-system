package com.example.presence.processing;

import com.example.presence.model.FingerprintScan;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("logout")
@Log(topic = "::processing::")
public class LogoutAccessProcessor implements AccessProcessor {
    @Override
    public void processScan(FingerprintScan scan) {
        log.info("LOGOUT scan");
    }
}
