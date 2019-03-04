package com.example.presence.common;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class TimeUtilsTest {

    @Test
    public void testSinceEpoch() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);

        TimeUtils tr = new TimeUtils();

        assertEquals(0, tr.yearsSinceEpoch(simpleDateFormat.parse("1970-11-02T02:50:32.908Z")));
        assertEquals(10, tr.monthsSinceEpoch(simpleDateFormat.parse("1970-11-02T02:50:32.908Z")));
        assertEquals(43, tr.weeksSinceEpoch(simpleDateFormat.parse("1970-11-02T02:50:32.908Z")));
        assertEquals(304, tr.daysSinceEpoch(simpleDateFormat.parse("1970-11-02T02:50:32.908Z")));

        assertEquals(5, tr.yearsSinceEpoch(simpleDateFormat.parse("1975-11-02T02:50:32.908Z")));
        assertEquals(70, tr.monthsSinceEpoch(simpleDateFormat.parse("1975-11-02T02:50:32.908Z")));
        assertEquals(304, tr.weeksSinceEpoch(simpleDateFormat.parse("1975-11-02T02:50:32.908Z")));
        assertEquals(2130, tr.daysSinceEpoch(simpleDateFormat.parse("1975-11-02T02:50:32.908Z")));

        assertEquals(49, tr.yearsSinceEpoch(simpleDateFormat.parse("2019-03-03T22:12:12.000Z")));
        assertEquals(590, tr.monthsSinceEpoch(simpleDateFormat.parse("2019-03-03T22:12:12.000Z")));
        assertEquals(2565, tr.weeksSinceEpoch(simpleDateFormat.parse("2019-03-03T22:12:12.000Z")));
        assertEquals(17958, tr.daysSinceEpoch(simpleDateFormat.parse("2019-03-03T22:12:12.000Z")));
    }

}
