package com.example.presence.reporting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePresenceReport {
    private String employeeId;
    private Date dateFrom;
    private Date dateTo;
    private Integer presenceMinutes;
    private TimePeriod period;
}
