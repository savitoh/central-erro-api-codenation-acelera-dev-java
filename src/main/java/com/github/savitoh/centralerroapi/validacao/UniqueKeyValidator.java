package com.github.savitoh.centralerroapi.validacao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;


public class UniqueKeyValidator implements ConstraintValidator<UniqueKey, Serializable> {

    @PersistenceContext
    private final EntityManager entityManager;

    private String columnName;

    private Class<?> entityClass;

    public UniqueKeyValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(UniqueKey constraintAnnotation) {
        this.columnName = constraintAnnotation.columnName();
        this.entityClass = constraintAnnotation.className();
    }

    @Override
    public boolean isValid(Serializable value, ConstraintValidatorContext context) {

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<?> root = query.from(entityClass);

        query.select(builder.count(root));
        query.where(builder.equal(root.get(columnName), value));

        return entityManager.createQuery(query).getSingleResult() == 0L;
    }
}
