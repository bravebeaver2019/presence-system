package com.example.presence.reporting;

import com.example.presence.reporting.model.EmployeePresenceReport;
import com.example.presence.reporting.model.TimePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeReportingControllerTest {

    @InjectMocks
    EmployeeReportingController controller;

    @Mock
    EmployeeReportingService reportingService;

    @Test(expected = InvalidPeriodException.class)
    public void testWrongPeriod() throws InvalidPeriodException {
        controller.getPresenceReport("employee", "WRONG", new Date());
    }

    @Test
    public void testGetReport() throws InvalidPeriodException {
        Date now = new Date();
        EmployeePresenceReport presenceReport = controller.
                getPresenceReport("employee", "week", now);
        verify(reportingService).getPresenceReport("employee", TimePeriod.week, now);
    }

}
