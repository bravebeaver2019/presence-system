package com.example.presence.processing;

import com.example.presence.model.Access;
import com.example.presence.model.FingerprintScan;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AccessEventProcessorFactoryTest {

    @Test
    public void testLogin() {

        FingerprintScan scan = new FingerprintScan("4d8276c6732e92fd37fe6a3f9f58284a",
                new Date(), Access.LOGIN);
        AccessProcessorFactory factory = new AccessProcessorFactory();
        factory.login = new LoginAccessEventProcessor();
        AccessEventProcessor accessEventProcessor = factory.getAccessProcessor(scan);

        assertEquals("Processor class retrieved",
                LoginAccessEventProcessor.class, accessEventProcessor.getClass());
    }

    @Test
    public void testLogout() {

        FingerprintScan scan = new FingerprintScan("4d8276c6732e92fd37fe6a3f9f58284a",
                new Date(), Access.LOGOUT);
        AccessProcessorFactory factory = new AccessProcessorFactory();
        factory.logout = new LogoutAccessEventProcessor();
        AccessEventProcessor accessEventProcessor = factory.getAccessProcessor(scan);

        assertEquals("Processor class retrieved",
                LogoutAccessEventProcessor.class, accessEventProcessor.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void testUnknownAccesType() {

        FingerprintScan scan = new FingerprintScan("4d8276c6732e92fd37fe6a3f9f58284a",
                new Date(), null);
        AccessProcessorFactory factory = new AccessProcessorFactory();
        factory.getAccessProcessor(scan);
    }
}
