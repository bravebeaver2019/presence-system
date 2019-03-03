package com.example.presence.capture;

import com.example.presence.common.model.FingerprintScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * reactive implementation of fingerprint scans.
 * it just relies on the service the processing of the data captured.
 */
@RestController
@RequestMapping("/scan")
public class FingerprintScanController {

    @Autowired
    FingerprintScanService scanService;

    @PostMapping("/read")
    public Mono<Void> scanFingerprint(@RequestBody FingerprintScan scan) {
        // I will store the external object with no translation to internal model
        return scanService.readScan(scan);
    }
}
