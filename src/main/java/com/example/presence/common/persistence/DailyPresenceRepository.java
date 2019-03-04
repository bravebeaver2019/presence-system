package com.example.presence.common.persistence;

import com.example.presence.common.model.DailyPresence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface DailyPresenceRepository extends CrudRepository<DailyPresence, Long> {

    @Query("SELECT SUM(dp.presenceMinutes) FROM DailyPresence dp " +
            "WHERE dp.userId=:userId " +
            "AND dp.daysSinceEpoch>=:dayFrom " +
            "AND dp.daysSinceEpoch<:dayTo ")
    int presenceMinutesDayPeriod(@Param("userId") String userId,
                                 @Param("dayFrom") int dayFrom,
                                 @Param("dayTo") int dayTo);

    @Query("SELECT SUM(dp.presenceMinutes) FROM DailyPresence dp " +
            "WHERE dp.userId=:userId " +
            "AND dp.weeksSinceEpoch>=:weekFrom " +
            "AND dp.weeksSinceEpoch<:weekTo ")
    int presenceMinutesWeekPeriod(@Param("userId") String userId,
                                  @Param("weekFrom") int weekFrom,
                                  @Param("weekTo") int weekTo);


    @Query("SELECT dp.userId, SUM(dp.presenceMinutes) FROM DailyPresence dp " +
            "WHERE dp.daysSinceEpoch=:day " +
            "GROUP BY dp.userId")
    Map presencePerDayPerEmployee(@Param("day") int day);

    @Query("SELECT dp.userId, SUM(dp.presenceMinutes) FROM DailyPresence dp " +
            "GROUP BY dp.userId")
    Map presencePerDayPerEmployee2();
}
