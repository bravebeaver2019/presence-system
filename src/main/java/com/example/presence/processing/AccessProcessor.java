package com.example.presence.processing;

import com.example.presence.model.FingerprintScan;

public interface AccessProcessor {

    void processScan(FingerprintScan scan);
}
