package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.common.BaseSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventoLogFiltroSpecificationsTest {

    private final BaseSpecification<EventoLog, EventoLogFiltro> specification = new EventoLogFiltroSpecifications();

    @Test
    void should_throw_exception_when_filter_null() {
        assertThrows(
                RuntimeException.class,
                ()-> specification.of(null),
                "[Assertion failed] - this argument is required; it must not be null"
        );
    }
}