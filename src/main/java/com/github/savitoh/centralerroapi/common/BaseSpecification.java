package com.github.savitoh.centralerroapi.common;

import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.Optional;

public abstract class BaseSpecification<T, F> {

    public abstract Specification<T> of(F filter);

    protected Optional<Specification<T>> conjuctionPredicateIfNull(final Object value) {
        if(Objects.isNull(value)) {
            return Optional.of((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        }
        return Optional.empty();
    }
}
