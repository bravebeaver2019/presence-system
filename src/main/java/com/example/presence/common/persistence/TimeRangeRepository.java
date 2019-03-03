package com.example.presence.common.persistence;

import com.example.presence.common.model.TimeRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeRangeRepository extends CrudRepository<TimeRange, Long> {

    @Query("SELECT tr FROM TimeRange tr WHERE tr.logoutDate is null AND tr.userId=:userId ORDER BY tr.loginDate DESC")
    List<TimeRange> findLastUnclosedRanges(@Param("userId") String userId);
}
