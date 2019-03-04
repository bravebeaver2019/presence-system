package com.example.presence.reporting;

import com.example.presence.common.TimeUtils;
import com.example.presence.common.persistence.DailyPresenceRepository;
import com.example.presence.reporting.model.EmployeePresence;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log
public class ScheduledReports {

    @Autowired
    DailyPresenceRepository repository;
    @Autowired
    TimeUtils utils;
    @Value("${min.presence.daily.threshold.minutes}")
    private int threshold;

    /**
     * This report will inform daily about all the employees with presence in the
     * previous day with the number of minutes.
     * empl1, 35 minutes
     * empl2, 367 minutes
     * ...
     *
     * @return the presence report
     */
    @Scheduled(fixedRateString = "${report.daily.fixed.rate.millis}")
    public List<EmployeePresence> reportLastDayPresencePerEmployee() {
        // here we should create an email and fill with the yesterday presence information
        return repository.presenceOnDayPerEmployee(utils.yesterdayInDaysSinceEpoch());
    }
    // todo: same reports for week, nonth, year to be done

    /**
     * This report will inform daily about all the employees with presence in the
     * previous day with who spent less than the expected minutes per day.
     * empl1, 35 minutes
     * empl2, 367 minutes
     * ...
     *
     * @return the presence report
     */
    @Scheduled(fixedRateString = "${report.daily.fixed.rate.millis}")
    public List<EmployeePresence> reportLastDayPresenceBelowThresholdPerEmployee() {
        // here we should create an email and fill with the employees that violated the rule
        // of minimum presence
        List<EmployeePresence> employeePresences = repository.
                presenceOnDayPerEmployee(utils.yesterdayInDaysSinceEpoch());
        return employeePresences.stream().filter(
                presence -> threshold < presence.getMinutes()
        ).collect(Collectors.toList());
    }
    // todo: same reports for week, nonth, year to be done

}
