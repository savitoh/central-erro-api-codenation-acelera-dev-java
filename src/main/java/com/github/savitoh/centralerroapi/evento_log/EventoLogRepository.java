package com.github.savitoh.centralerroapi.evento_log;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoLogRepository extends PagingAndSortingRepository<EventoLog, Long>, JpaSpecificationExecutor<EventoLog> {
}
