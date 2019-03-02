package com.example.presence.common;

import com.example.presence.model.FingerprintScan;

public interface ScanObserver {

    /**
     * please tell me whenever a new scan is available.
     *
     * @param scan the scan I want
     */
    void notify(FingerprintScan scan);

}
