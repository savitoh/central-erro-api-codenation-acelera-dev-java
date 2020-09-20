package com.github.savitoh.centralerroapi.validacao;

import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TipoLogLevelExistsValidator implements ConstraintValidator<TipoLogLevelExists, Object> {


    @Override
    public void initialize(TipoLogLevelExists constraintAnnotation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        if(Objects.isNull(target) || !Short.class.isAssignableFrom(target.getClass())) {
            return false;
        }
        Short value = (Short) target;
        return TipoLogLevel.getById(value).isPresent();
    }
}
