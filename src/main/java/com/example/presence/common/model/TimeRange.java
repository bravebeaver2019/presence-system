package com.example.presence.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "TR_INDEX_1", columnList = "logoutDate,userId")})
public class TimeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutDate;

    /**
     * the number of minutes between login and logout..
     *
     * @return presence minutes
     */
    public Integer presenceMinutes() {
        Minutes minutes = Minutes.minutesBetween(new DateTime(loginDate), new DateTime(logoutDate));
        return minutes.getMinutes();
    }

    /**
     * says if the login and logout date happen in the same day.
     * @return true if same day
     */
    public boolean isSameDay() {
        Days days = Days.daysBetween(new DateTime(loginDate), new DateTime(logoutDate));
        return days.getDays() == 0;
    }

    /**
     * the number of years that passed since epoch to logout date
     *
     * @return years
     */
    public int yearsSinceEpoch() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Years.yearsBetween(epoch, new DateTime(logoutDate).withZone(DateTimeZone.UTC)).getYears();
    }

    /**
     * the number of months that passed since epoch to logout date
     *
     * @return months
     */
    public int monthsSinceEpoch() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Months.monthsBetween(epoch, new DateTime(logoutDate).withZone(DateTimeZone.UTC)).getMonths();
    }

    /**
     * the number of weeks that passed since epoch to logout date
     *
     * @return weeks
     */
    public int weeksSinceEpoch() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Weeks.weeksBetween(epoch, new DateTime(logoutDate).withZone(DateTimeZone.UTC)).getWeeks();
    }

    /**
     * the number of days that passed since epoch to logout date
     *
     * @return days
     */
    public int daysSinceEpoch() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        return Days.daysBetween(epoch, new DateTime(logoutDate).withZone(DateTimeZone.UTC)).getDays();
    }
}
