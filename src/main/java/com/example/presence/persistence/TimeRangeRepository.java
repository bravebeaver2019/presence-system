package com.example.presence.persistence;

import com.example.presence.model.TimeRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeRangeRepository extends CrudRepository<TimeRange, Long> {

    @Query("SELECT tr FROM TimeRange tr WHERE tr.logoutDate is null ORDER BY tr.loginDate DESC")
    List<TimeRange> findLastUnclosedRanges();
}
