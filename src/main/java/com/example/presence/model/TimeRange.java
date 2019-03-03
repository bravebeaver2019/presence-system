package com.example.presence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutDate;

    public Integer presenceMinutes() {
        Minutes minutes = Minutes.minutesBetween(new DateTime(loginDate), new DateTime(logoutDate));
        return minutes.getMinutes();
    }
}
