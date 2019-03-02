package com.example.presence.capture;

import com.example.presence.model.FingerprintScan;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Log
public class FingerprintScanServiceImpl implements FingerprintScanService {

    @Override
    public Mono<Void> readScan(FingerprintScan scan) {
        log.info("processing scan: " + scan);
        return null;
    }
}
