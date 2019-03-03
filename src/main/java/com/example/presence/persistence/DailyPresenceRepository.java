package com.example.presence.persistence;

import com.example.presence.model.DailyPresence;
import org.springframework.data.repository.CrudRepository;

public interface DailyPresenceRepository extends CrudRepository<DailyPresence, Long> {

}
