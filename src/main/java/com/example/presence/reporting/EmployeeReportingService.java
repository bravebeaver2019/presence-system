package com.example.presence.reporting;

import com.example.presence.common.TimeUtils;
import com.example.presence.common.persistence.DailyPresenceRepository;
import com.example.presence.reporting.model.EmployeePresenceReport;
import com.example.presence.reporting.model.TimePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmployeeReportingService {

    @Autowired
    DailyPresenceRepository repository;
    @Autowired
    TimeUtils utils;

    public EmployeePresenceReport getPresenceReport(String employeeId, TimePeriod period, Date date) {
        int presenceMinutes = 0;
        try {
            if (period.equals(TimePeriod.day)) {
                int daysFrom = utils.daysSinceEpoch(date);
                presenceMinutes = repository.presenceMinutesDayPeriod(employeeId, daysFrom, daysFrom + 1);
            } else if (period.equals(TimePeriod.week)) {
                int weeksFrom = utils.weeksSinceEpoch(date);
                presenceMinutes = repository.presenceMinutesWeekPeriod(employeeId, weeksFrom, weeksFrom + 1);
            } // todo: implement the other range reports and refactor to a factory pattern or other
        } catch (Exception e) { // no presence found for such date and period
        }
        return new EmployeePresenceReport(employeeId, date, date, presenceMinutes, period);
    }
}
