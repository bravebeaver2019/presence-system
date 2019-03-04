package com.example.presence.capture;

import com.example.presence.capture.persistence.Sender;
import com.example.presence.common.model.Access;
import com.example.presence.common.model.FingerprintScan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class FingerprintScanServiceTest {

    @InjectMocks
    FingerprintScanServiceImpl service;

    @Mock
    Sender sender;

    @Test
    public void testFingerprintScanService() {

        final Date now = new Date();
        final String fingerprintHash = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan scan = new FingerprintScan(fingerprintHash, now, Access.LOGIN);

        service.readScan(scan);
        Mockito.verify(sender).send(scan);
    }
}
