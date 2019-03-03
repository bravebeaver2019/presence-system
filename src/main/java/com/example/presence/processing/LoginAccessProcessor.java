package com.example.presence.processing;

import com.example.presence.model.FingerprintScan;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("login")
@Log(topic = "::processing::")
public class LoginAccessProcessor implements AccessProcessor {
    @Override
    public void processScan(FingerprintScan scan) {
        log.info("LOGIN scan");
    }
}
