package com.github.savitoh.centralerroapi.validacao;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
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
