package com.example.presence.common.model;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TimeRangeTest {

    @Test
    public void testMinutesDiff() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        TimeRange tr = new TimeRange();
        tr.setLoginDate(simpleDateFormat.parse("2011-11-02T02:50:12.208Z"));

        // this test verifies that any time range below one minute will be ignored
        tr.setLogoutDate(simpleDateFormat.parse("2011-11-02T02:50:32.908Z"));
        assertEquals(0, tr.presenceMinutes().intValue());

        tr.setLogoutDate(simpleDateFormat.parse("2011-11-02T02:51:12.208Z"));
        assertEquals(1, tr.presenceMinutes().intValue());

        tr.setLogoutDate(simpleDateFormat.parse("2011-11-02T22:51:12.208Z"));
        assertEquals(1201, tr.presenceMinutes().intValue());

        tr.setLogoutDate(simpleDateFormat.parse("2011-11-03T22:51:12.208Z"));
        assertEquals(2641, tr.presenceMinutes().intValue());
    }

    @Test
    public void testSameDay() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        TimeRange tr = new TimeRange();
        tr.setLoginDate(simpleDateFormat.parse("2011-11-02T02:50:12.208Z"));

        tr.setLogoutDate(simpleDateFormat.parse("2011-11-02T02:50:32.908Z"));
        assertTrue(tr.loggedOutSameDay());

        tr.setLogoutDate(simpleDateFormat.parse("2011-11-02T22:50:32.908Z"));
        assertTrue(tr.loggedOutSameDay());

        tr.setLogoutDate(simpleDateFormat.parse("2011-11-03T22:50:32.908Z"));
        assertFalse(tr.loggedOutSameDay());

    }
}
