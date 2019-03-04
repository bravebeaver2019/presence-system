package com.example.presence.reporting;

import com.example.presence.reporting.model.EmployeePresenceReport;
import com.example.presence.reporting.model.TimePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class EmployeeReportingController {

    @Autowired
    EmployeeReportingService reportingService;

    @RequestMapping(value = "/reporting/employee", method = RequestMethod.GET)
    public EmployeePresenceReport getPresenceReport(
            @RequestParam String employeeId,
            @RequestParam String period,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) throws InvalidPeriodException {
        try {
            TimePeriod timePeriod = TimePeriod.valueOf(period);
            return reportingService.getPresenceReport(employeeId, timePeriod, date);
        } catch (IllegalArgumentException e) {
            throw new InvalidPeriodException();
        }
    }
}
