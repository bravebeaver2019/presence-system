package com.example.presence;

import com.example.presence.capture.FingerprintScanController;
import com.example.presence.common.model.Access;
import com.example.presence.common.model.DailyPresence;
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
public class PresenceApplicationTests {

    @Autowired
    FingerprintScanController controller;
    @Autowired
    DailyPresenceRepository repository;

    @Test
    @DirtiesContext
    public void testSimpleLongLogout() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan login = new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T02:50:12.208Z"), Access.LOGIN);
        controller.scanFingerprint(login);

        FingerprintScan logout = new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T04:45:12.208Z"), Access.LOGOUT);
        controller.scanFingerprint(logout);

        DailyPresence presence = repository.findAll().iterator().next();
        assertEquals(userId, presence.getUserId());
        assertEquals(15279, presence.getDaysSinceEpoch());
        assertEquals(2182, presence.getWeeksSinceEpoch());
        assertEquals(502, presence.getMonthsSinceEpoch());
        assertEquals(41, presence.getYearsSinceEpoch());
        assertEquals(115, presence.getPresenceMinutes());
    }

    @Test
    @DirtiesContext
    public void testFirstLoginIgnored() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan previous = new FingerprintScan(userId,
                simpleDateFormat.parse("2010-11-02T02:50:12.208Z"), Access.LOGIN);
        controller.scanFingerprint(previous);
        FingerprintScan login = new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T02:50:12.208Z"), Access.LOGIN);
        controller.scanFingerprint(login);

        FingerprintScan logout = new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T04:45:12.208Z"), Access.LOGOUT);
        controller.scanFingerprint(logout);

        DailyPresence presence = repository.findAll().iterator().next();
        assertEquals(userId, presence.getUserId());
        assertEquals(15279, presence.getDaysSinceEpoch());
        assertEquals(2182, presence.getWeeksSinceEpoch());
        assertEquals(502, presence.getMonthsSinceEpoch());
        assertEquals(41, presence.getYearsSinceEpoch());
        assertEquals(115, presence.getPresenceMinutes());
    }

    @Test
    @DirtiesContext
    public void testSecondLogoutIgnored() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";
        FingerprintScan login = new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T02:50:12.208Z"), Access.LOGIN);
        controller.scanFingerprint(login);

        FingerprintScan logout = new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T04:45:12.208Z"), Access.LOGOUT);
        controller.scanFingerprint(logout);
        FingerprintScan logout2 = new FingerprintScan(userId,
                simpleDateFormat.parse("2011-11-02T04:50:12.208Z"), Access.LOGOUT);
        controller.scanFingerprint(logout2);

        DailyPresence presence = repository.findAll().iterator().next();
        assertEquals(userId, presence.getUserId());
        assertEquals(15279, presence.getDaysSinceEpoch());
        assertEquals(2182, presence.getWeeksSinceEpoch());
        assertEquals(502, presence.getMonthsSinceEpoch());
        assertEquals(41, presence.getYearsSinceEpoch());
        assertEquals(115, presence.getPresenceMinutes());
    }
}
