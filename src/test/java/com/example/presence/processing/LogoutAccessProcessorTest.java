package com.example.presence.processing;

import com.example.presence.common.model.Access;
import com.example.presence.common.model.DailyPresence;
import com.example.presence.common.model.FingerprintScan;
import com.example.presence.common.model.TimeRange;
import com.example.presence.common.persistence.DailyPresenceRepository;
import com.example.presence.common.persistence.TimeRangeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LogoutAccessProcessorTest {

    @InjectMocks
    LogoutAccessEventProcessor processor = new LogoutAccessEventProcessor();
    @Mock
    TimeRangeRepository timeRangeRepository;
    @Mock
    DailyPresenceRepository presenceRepository;

    @Test
    public void testNoOpenLoginFound() {
        String hash = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan scan = new FingerprintScan(hash, new Date(), Access.LOGIN);
        ArrayList<TimeRange> emptyResult = new ArrayList<>();
        when(timeRangeRepository.findLastUnclosedRanges(hash)).thenReturn(emptyResult);
        processor.processScanEvent(scan);
    }

    @Test
    public void testOpenLoginFound() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);
        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";

        Date loginDate = simpleDateFormat.parse("2013-11-02T02:50:32.908Z");
        Date logoutDate = simpleDateFormat.parse("2013-11-02T12:50:32.908Z");

        FingerprintScan logoutEvent = new FingerprintScan(userId, logoutDate, Access.LOGIN);
        TimeRange timeRange = TimeRange.builder()
                .userId(userId)
                .loginDate(loginDate)
                .build();
        ArrayList<TimeRange> ranges = new ArrayList<>();
        ranges.add(timeRange);

        when(timeRangeRepository.findLastUnclosedRanges(userId)).thenReturn(ranges);
        processor.processScanEvent(logoutEvent);
        timeRange.setLogoutDate(logoutDate);
        verify(timeRangeRepository).save(timeRange);
        verify(presenceRepository).save(
                DailyPresence.builder()
                        .userId(userId)
                        .presenceMinutes(600)
                        .yearsSinceEpoch(43)
                        .monthsSinceEpoch(526)
                        .weeksSinceEpoch(2287)
                        .daysSinceEpoch(16011)
                        .build()
        );
    }
}
