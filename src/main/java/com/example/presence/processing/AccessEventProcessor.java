package com.example.presence.processing;

import com.example.presence.model.FingerprintScan;

public interface AccessEventProcessor {

    void processScanEvent(FingerprintScan scan);
}
