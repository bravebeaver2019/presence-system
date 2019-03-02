package com.example.presence.capture;

import com.example.presence.model.FingerprintScan;
import reactor.core.publisher.Mono;

public interface FingerprintScanService {

    Mono<Void> readScan(FingerprintScan scan);
}
