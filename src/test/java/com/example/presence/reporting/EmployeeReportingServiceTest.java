package com.example.presence.reporting;

import com.example.presence.common.TimeUtils;
import com.example.presence.common.persistence.DailyPresenceRepository;
import com.example.presence.reporting.model.EmployeePresenceReport;
import com.example.presence.reporting.model.TimePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeReportingServiceTest {

    @InjectMocks
    EmployeeReportingService service;
    @Mock
    DailyPresenceRepository repository;
    @Mock
    TimeUtils utils;

    @Test
    public void testDayPresenceReport() {
        Date date = new Date();
        String employee = "employee";
        when(utils.daysSinceEpoch(date)).thenReturn(1);
        when(repository.presenceMinutesDayPeriod(employee, 1, 2)).thenReturn(201);
        EmployeePresenceReport presenceReport = service.getPresenceReport(employee, TimePeriod.day, date);
        assertEquals("presence minutes", new Integer(201), presenceReport.getPresenceMinutes());
        assertEquals("presence employee", employee, presenceReport.getEmployeeId());
        assertEquals("presence period", TimePeriod.day, presenceReport.getPeriod());
        assertEquals("presence date from", date, presenceReport.getDateFrom());
        assertEquals("presence date to", date, presenceReport.getDateTo());
    }

    @Test
    public void testWeekPresenceReport() {
        Date date = new Date();
        String employee = "employee";
        when(utils.weeksSinceEpoch(date)).thenReturn(3);
        when(repository.presenceMinutesWeekPeriod(employee, 3, 4)).thenReturn(124);
        EmployeePresenceReport presenceReport = service.getPresenceReport(employee, TimePeriod.week, date);
        assertEquals("presence minutes", new Integer(124), presenceReport.getPresenceMinutes());
        assertEquals("presence employee", employee, presenceReport.getEmployeeId());
        assertEquals("presence period", TimePeriod.week, presenceReport.getPeriod());
        assertEquals("presence date from", date, presenceReport.getDateFrom());
        assertEquals("presence date to", date, presenceReport.getDateTo());
    }

}
