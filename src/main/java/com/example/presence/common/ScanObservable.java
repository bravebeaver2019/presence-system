package com.example.presence.common;

public interface ScanObservable {

    /**
     * this observer is interested in scans.
     * @param observer the interested observer
     */
    void addObserver(ScanObserver observer);
}
