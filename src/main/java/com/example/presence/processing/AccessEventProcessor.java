package com.example.presence.processing;

import com.example.presence.common.model.FingerprintScan;

/**
 * this component will be in charge to process the different types of events
 * that will be received from the stream.
 */
public interface AccessEventProcessor {

    /**
     * processes an scan event.
     *
     * @param scan the event
     */
    void processScanEvent(FingerprintScan scan);
}
