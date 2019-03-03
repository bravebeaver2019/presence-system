package com.example.presence.capture.persistence;

import com.example.presence.common.ScanObservable;
import com.example.presence.common.ScanObserver;
import com.example.presence.model.FingerprintScan;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * primary impl we will use for demo in alternative to kafka impl.
 * its a pub/sub simulator for demo purposes.
 */
@Component
@Primary
@Log(topic = "::capture::")
public class DemoSenderImpl implements Sender, ScanObservable {

    private List<ScanObserver> observers = new ArrayList<>();

    @Override
    public void send(FingerprintScan scan) {
        log.info("received scan: " + scan + "notifying listeners");
        // quick and dirty way to simulate an asynchronous pub/sub system
        observers.stream().parallel().forEach(obs -> obs.notify(scan));
    }

    @Override
    public void addObserver(ScanObserver observer) {
        observers.add(observer);
    }
}
