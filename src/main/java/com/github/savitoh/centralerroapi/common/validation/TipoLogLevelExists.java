package com.github.savitoh.centralerroapi.common.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, CONSTRUCTOR, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = TipoLogLevelExistsValidator.class)
@Documented
public @interface TipoLogLevelExists {

    String message() default "Não existe LogLevel com este codigo.";

    boolean permitNull() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
