package com.github.savitoh.centralerroapi.common;

import com.github.savitoh.centralerroapi.evento_log.EventoLog;
import com.github.savitoh.centralerroapi.evento_log.EventoLogFiltroSpecifications;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;

class BaseSpecificationTest {

    private final BaseSpecification<EventoLog, ?> baseSpecification = new EventoLogFiltroSpecifications();

    @Test
    void should_throw_runtimeException_when_specificationInputNull() {
        assertThrows(RuntimeException.class,
                ()-> baseSpecification.specificationOrDefaultIfNull(null, null),
                "[Assertion failed] - this argument is required; it must not be null"
        );
    }

    @Test
    void should_return_defaultSpecification_when_inputNull() {
        Specification<EventoLog> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and();

        Specification<EventoLog> specificationResult =
                baseSpecification.specificationOrDefaultIfNull(null, specification);

        assertNotNull(specificationResult);
        assertNotEquals(specification, specificationResult);
    }

    @Test
    void should_return_specification_when_inputNotNull() {
        Specification<EventoLog> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and();

        Specification<EventoLog> specificationResult =
                baseSpecification.specificationOrDefaultIfNull(new Object(), specification);

        assertNotNull(specificationResult);
        assertEquals(specification, specificationResult);
    }

}