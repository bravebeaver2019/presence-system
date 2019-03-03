package com.example.presence.processing;

import com.example.presence.model.Access;
import com.example.presence.model.FingerprintScan;
import com.example.presence.model.TimeRange;
import com.example.presence.persistence.TimeRangeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;


@RunWith(MockitoJUnitRunner.class)
public class LoginAccessEventProcessorTest {

    @InjectMocks
    LoginAccessEventProcessor processor = new LoginAccessEventProcessor();

    @Mock
    TimeRangeRepository repository;

    @Test
    public void testProcessLoginEvent() {
        Date now = new Date();
        String fingerprintHash = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan scan = new FingerprintScan(fingerprintHash,
                now, Access.LOGIN);

        processor.processScanEvent(scan);
        TimeRange range = TimeRange.builder().loginDate(now).userId(fingerprintHash).build();
        Mockito.verify(repository).save(range);
    }
}
