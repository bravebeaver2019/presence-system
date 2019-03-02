package com.example.presence.capture;

import com.example.presence.capture.persistence.Sender;
import com.example.presence.model.FingerprintScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FingerprintScanServiceImpl implements FingerprintScanService {

    @Autowired
    Sender sender;

    @Override
    public Mono<Void> readScan(FingerprintScan scan) {
        sender.send(scan);
        return null;
    }
}
