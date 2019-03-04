package com.example.presence.capture;

import com.example.presence.common.model.FingerprintScan;
import reactor.core.publisher.Mono;

/**
 * persists a scan event in the log.
 */
public interface FingerprintScanService {

    Mono<Void> readScan(FingerprintScan scan);
}
