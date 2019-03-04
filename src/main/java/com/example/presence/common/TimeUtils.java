package com.example.presence.common;

import org.joda.time.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeUtils {

    /**
     * the number of days that passed since epoch to given date.
     *
     * @return days
     */
    public int daysSinceEpoch(Date date) {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Days.daysBetween(epoch, new DateTime(date)).getDays();
    }

    /**
     * the number of weeks that passed since epoch to given date.
     *
     * @return weeks
     */
    public int weeksSinceEpoch(Date date) {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Weeks.weeksBetween(epoch, new DateTime(date)).getWeeks();
    }

    /**
     * the number of months that passed since epoch to logout date.
     *
     * @return months
     */
    public int monthsSinceEpoch(Date date) {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Months.monthsBetween(epoch, new DateTime(date).withZone(DateTimeZone.UTC)).getMonths();
    }

    /**
     * the number of years that passed since epoch to logout date.
     *
     * @return years
     */
    public int yearsSinceEpoch(Date date) {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Years.yearsBetween(epoch, new DateTime(date).withZone(DateTimeZone.UTC)).getYears();
    }

    /**
     * yesterday as days since epoch.
     *
     * @return yesterday
     */
    public int yesterdayInDaysSinceEpoch() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Days.daysBetween(epoch,
                new DateTime(new Date()).withZone(DateTimeZone.UTC)).getDays() - 1;
    }

}
