package com.example.presence.common.persistence;

import com.example.presence.common.model.DailyPresence;
import org.springframework.data.repository.CrudRepository;

public interface DailyPresenceRepository extends CrudRepository<DailyPresence, Long> {

}
