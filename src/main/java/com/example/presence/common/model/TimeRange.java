package com.example.presence.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity lives a temporal PROCESSING stage, its used to compute time diffs
 * between async login and logout events.
 */
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
    public boolean loggedOutSameDay() {
        Days days = Days.daysBetween(new DateTime(loginDate), new DateTime(logoutDate));
        return days.getDays() == 0;
    }
}
