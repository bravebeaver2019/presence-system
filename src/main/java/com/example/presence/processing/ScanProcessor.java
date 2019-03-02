package com.example.presence.processing;

import com.example.presence.common.ScanObservable;
import com.example.presence.common.ScanObserver;
import com.example.presence.model.FingerprintScan;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * this component is responsible to listen to all scans stored in the system log
 * (kafka) and process and populate the derived database.
 */
@Component
@Log
public class ScanProcessor implements ScanObserver {

    @Autowired
    ScanObservable observable;

    @PostConstruct
    public void init() {
        // please, allow me to listen to scans
        observable.addObserver(this);
    }

    @Override
    public void notify(FingerprintScan scan) {
        log.info(":: processing :: received scan " + scan);
        // for login accesses we just update the database with the latest login
        // for logout we have to compute the total presence time since the last login
    }
}
