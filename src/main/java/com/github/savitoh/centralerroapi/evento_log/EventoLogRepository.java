package com.github.savitoh.centralerroapi.evento_log;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoLogRepository extends CrudRepository<EventoLog, Long> {
}
