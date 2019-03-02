package com.example.presence.capture.persistence;

import com.example.presence.model.FingerprintScan;

public interface Sender {

    void send(FingerprintScan scan);

}
