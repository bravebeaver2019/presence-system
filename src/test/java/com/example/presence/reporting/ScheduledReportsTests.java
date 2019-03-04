package com.example.presence.reporting;

import com.example.presence.capture.FingerprintScanController;
import com.example.presence.common.model.Access;
import com.example.presence.common.model.FingerprintScan;
import com.example.presence.reporting.model.EmployeePresence;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduledReportsTests {

    @Autowired
    FingerprintScanController controller;
    @Autowired
    ScheduledReports reports;

    @Test
    @DirtiesContext
    public void testYesterdayPresence() throws ParseException {

        DateTime oneDayAgo = new DateTime().minusDays(1);

        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";
        controller.scanFingerprint(new FingerprintScan(userId,
                oneDayAgo.plusHours(6).toDate(), Access.LOGIN));
        controller.scanFingerprint(new FingerprintScan(userId,
                oneDayAgo.plusHours(9).toDate(), Access.LOGOUT));

        String anotherUserId = "e24f6bb4f74af4c9c0f2cf8c0254969d";
        controller.scanFingerprint(new FingerprintScan(anotherUserId,
                oneDayAgo.plusHours(7).plusMinutes(25).toDate(), Access.LOGIN));
        controller.scanFingerprint(new FingerprintScan(anotherUserId,
                oneDayAgo.plusHours(8).toDate(), Access.LOGOUT));

        List<EmployeePresence> report = reports.reportLastDayPresencePerEmployee();
        report.stream().forEach(p -> {
            if (p.getEmployeeId().equals(userId)) {
                assertEquals(new Long(180), p.getMinutes());
            } else if (p.getEmployeeId().equals(anotherUserId)) {
                assertEquals(new Long(35), p.getMinutes());
            }
        });
    }

    @Test
    @DirtiesContext
    public void testYesterdayPresenceBelowThreshold() throws ParseException {

        DateTime oneDayAgo = new DateTime().minusDays(1);

        String userId = "4d8276c6732e92fd37fe6a3f9f58284a";
        controller.scanFingerprint(new FingerprintScan(userId,
                oneDayAgo.plusHours(6).toDate(), Access.LOGIN));
        controller.scanFingerprint(new FingerprintScan(userId,
                oneDayAgo.plusHours(9).toDate(), Access.LOGOUT));

        String anotherUserId = "e24f6bb4f74af4c9c0f2cf8c0254969d";
        controller.scanFingerprint(new FingerprintScan(anotherUserId,
                oneDayAgo.plusHours(7).plusMinutes(25).toDate(), Access.LOGIN));
        controller.scanFingerprint(new FingerprintScan(anotherUserId,
                oneDayAgo.plusHours(8).toDate(), Access.LOGOUT));

        List<EmployeePresence> report = reports.reportLastDayPresenceBelowThresholdPerEmployee();
        assertEquals("only one employee was below the threshold", 1, report.size());
        report.stream().forEach(p -> {
            if (p.getEmployeeId().equals(anotherUserId)) {
                assertEquals(new Long(35), p.getMinutes());
            }
        });
    }

}
