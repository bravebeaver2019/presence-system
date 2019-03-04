package com.example.presence.reporting;

import com.example.presence.capture.FingerprintScanController;
import com.example.presence.common.model.Access;
import com.example.presence.common.model.FingerprintScan;
import com.example.presence.common.persistence.DailyPresenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresenceReportingTests {

    @Autowired
    FingerprintScanController controller;
    @Autowired
    DailyPresenceRepository repository;

    @Test
    @DirtiesContext
    public void testOnePresence() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";
        controller.scanFingerprint(new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T02:50:12.208Z"), Access.LOGIN));
        controller.scanFingerprint(new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T04:45:12.208Z"), Access.LOGOUT));

        int minutesInDay = repository.presenceMinutesDayPeriod(userId, 15279, 15280);
        assertEquals(115, minutesInDay);
    }

    @Test
    @DirtiesContext
    public void testTwoPresences() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";
        controller.scanFingerprint(new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T02:50:12.208Z"), Access.LOGIN));
        controller.scanFingerprint(new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T04:45:12.208Z"), Access.LOGOUT));
        controller.scanFingerprint(new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T05:50:12.208Z"), Access.LOGIN));
        controller.scanFingerprint(new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T05:55:12.208Z"), Access.LOGOUT));

        int minutesInDay = repository.presenceMinutesDayPeriod(userId, 15279, 15280);
        assertEquals(120, minutesInDay);
    }

}
