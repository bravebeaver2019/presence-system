package com.example.presence.processing;

import com.example.presence.common.ScanObservable;
import com.example.presence.model.Access;
import com.example.presence.model.FingerprintScan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ScanProcessorTest {

    @InjectMocks
    ScanProcessor processor = new ScanProcessor();
    @Mock
    ScanObservable observable;
    @Mock
    AccessProcessorFactory factory;
    @Mock
    AccessEventProcessor accessEventProcessor;

    @Test
    public void testScanProcessorInit() {
        processor.init();
        Mockito.verify(observable).addObserver(processor);
    }

    @Test
    public void testNotify() {
        FingerprintScan scan = new FingerprintScan("4d8276c6732e92fd37fe6a3f9f58284a",
                new Date(), Access.LOGIN);
        when(factory.getAccessProcessor(scan)).thenReturn(accessEventProcessor);
        processor.notify(scan);
        Mockito.verify(factory).getAccessProcessor(scan);
        Mockito.verify(accessEventProcessor).processScanEvent(scan);
    }
}
