package com.example.presence.capture;

import com.example.presence.capture.persistence.EventSender;
import com.example.presence.common.model.FingerprintScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FingerprintScanServiceImpl implements FingerprintScanService {

    @Autowired
    EventSender eventSender;

    /**
     * used to persist a scan event in the log.
     *
     * @param scan the scan event
     * @return null
     */
    @Override
    public Mono<Void> readScan(FingerprintScan scan) {
        eventSender.send(scan);
        return null;
    }
}
