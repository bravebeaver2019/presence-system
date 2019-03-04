package com.example.presence.processing;

import com.example.presence.common.ScanObservable;
import com.example.presence.common.ScanObserver;
import com.example.presence.common.model.FingerprintScan;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * this component is responsible to listen to all scans stored in the system log
 * (kafka) and process and populate the derived database.
 */
@Component
@Log(topic = "::processing::")
public class ScanProcessor implements ScanObserver {

    @Autowired
    ScanObservable observable;

    @Autowired
    AccessProcessorFactory factory;

    @PostConstruct
    public void init() {
        observable.addObserver(this);
    }

    @Override
    public void notify(FingerprintScan scan) {
        log.info("received scan " + scan);
        factory.getAccessProcessor(scan).processScanEvent(scan);
    }
}
