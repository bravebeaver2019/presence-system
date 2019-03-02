package com.example.presence.capture.persistence;

import com.example.presence.common.ScanObserver;
import com.example.presence.model.Access;
import com.example.presence.model.FingerprintScan;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.verify;

public class DemoSenderImplTest {

    @Test
    public void testNotifications() {

        final Date now = new Date();
        final String fingerprintHash = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan scan = new FingerprintScan(fingerprintHash, now, Access.LOGIN);

        DemoSenderImpl sender = new DemoSenderImpl();
        ScanObserver observer1 = Mockito.mock(ScanObserver.class);
        ScanObserver observer2 = Mockito.mock(ScanObserver.class);

        sender.addObserver(observer1);
        sender.addObserver(observer2);

        sender.send(scan);

        verify(observer1).notify(scan);
        verify(observer2).notify(scan);
    }
}
