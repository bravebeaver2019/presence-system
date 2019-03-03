package com.example.presence.capture.persistence;

import com.example.presence.common.model.FingerprintScan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSenderImpl implements Sender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(FingerprintScan scan) {
        try {
            // note the partition policy used (scan fingerprint) it will mean that regarless the
            // number of topic partitions, same user will always be processed by same cosumer
            kafkaTemplate.send("scans", scan.getFingerprintHash(),
                    new ObjectMapper().writeValueAsString(scan));
        } catch (JsonProcessingException e) {
            // todo: a better exception/error control functionality should be implemented
            // todo: instead of an internal error, this should mean a bad request being notified to client
            throw new RuntimeException("invalid scan received: " + scan);
        }
    }
}
