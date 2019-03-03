package com.example.presence.processing;

import com.example.presence.common.model.FingerprintScan;

public interface AccessEventProcessor {

    void processScanEvent(FingerprintScan scan);
}
