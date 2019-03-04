package com.example.presence.reporting;

import com.example.presence.common.persistence.DailyPresenceRepository;
import com.example.presence.reporting.model.EmployeePresenceReport;
import com.example.presence.reporting.model.TimePeriod;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmployeeReportingService {

    @Autowired
    DailyPresenceRepository repository;

    public EmployeePresenceReport getPresenceReport(String employeeId, TimePeriod period, Date date) {
        int presenceMinutes = 0;
        try {
            if (period.equals(TimePeriod.day)) {
                MutableDateTime epoch = new MutableDateTime();
                epoch.setDate(0);
                int daysFrom = Days.daysBetween(epoch, new DateTime(date)).getDays();
                presenceMinutes = repository.presenceMinutesDayPeriod(employeeId, daysFrom, daysFrom + 1);
            } else if (period.equals(TimePeriod.week)) {
                MutableDateTime epoch = new MutableDateTime();
                epoch.setDate(0);
                int weeksFrom = Weeks.weeksBetween(epoch, new DateTime(date)).getWeeks();
                presenceMinutes = repository.presenceMinutesWeekPeriod(employeeId, weeksFrom, weeksFrom + 1);
            } // todo: implement the other range reports and refactor to a factory pattern or other
        } catch (Exception e) {
            // no presence found for such date and period
        }
        return new EmployeePresenceReport(employeeId, date, date, presenceMinutes, period);
    }
}
