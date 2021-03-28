package com.github.savitoh.centralerroapi.common;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Objects;

public abstract class BaseSpecification<T, F> {

    public abstract Specification<T> of(F filter);

    protected Specification<T> specificationOrDefaultIfNull(final Object value, @NonNull final Specification<T> specification) {
        Assert.notNull(specification, "[Assertion failed] - this argument is required; it must not be null");
        if(Objects.isNull(value)) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return specification;
    }
}
