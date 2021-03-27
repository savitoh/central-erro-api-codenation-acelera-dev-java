package com.github.savitoh.centralerroapi.common;

import com.github.savitoh.centralerroapi.evento_log.EventoLogFiltroSpecifications;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseSpecificationTest {

    private final BaseSpecification<?, ?> baseSpecification = new EventoLogFiltroSpecifications();

    @Test
    void should_return_conjuction_specification_when_input_null() {
        Optional<? extends Specification<?>> specification = baseSpecification.conjuctionPredicateIfNull(null);

        assertTrue(specification.isPresent());
    }

    @Test
    void should_return_empty_when_input_not_null() {
        Optional<? extends Specification<?>> specification = baseSpecification.conjuctionPredicateIfNull(new Object());

        assertTrue(specification.isEmpty());
    }

}