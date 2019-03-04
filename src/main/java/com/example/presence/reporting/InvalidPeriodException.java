package com.example.presence.reporting;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "period param should be one of year, month, week, day")
public class InvalidPeriodException extends Exception {

}
