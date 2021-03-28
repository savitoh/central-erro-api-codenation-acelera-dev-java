package com.github.savitoh.centralerroapi.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, CONSTRUCTOR, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueKeyValidator.class)
@Documented
public @interface UniqueKey {

    String columnName();

    Class<?> className();

    String message() default "{UniqueKey.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
