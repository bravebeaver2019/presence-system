package com.example.presence.capture;

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
public class FingerprintScanControllerTest {

    @InjectMocks
    FingerprintScanController controller;

    @Mock FingerprintScanService scanService;

    @Test
    public void testFingerprintScanController() {

        final Date now = new Date();
        final String fingerprintHash = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan scan = new FingerprintScan(fingerprintHash, now, Access.LOGIN);

        controller.scanFingerprint(scan);
        Mockito.verify(scanService).readScan(scan);
    }

}
