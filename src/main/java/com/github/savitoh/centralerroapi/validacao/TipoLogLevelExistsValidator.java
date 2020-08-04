package com.github.savitoh.centralerroapi.validacao;

import com.github.savitoh.centralerroapi.evento.tipologlevel.TipoLogLevel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TipoLogLevelExistsValidator implements ConstraintValidator<TipoLogLevelExists, Object> {


    @Override
    public void initialize(TipoLogLevelExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value instanceof Short) {
            Short codigoTipoLogLevel = (Short) value;
            return TipoLogLevel.getById(codigoTipoLogLevel).isPresent();
        }
        return false;
    }
}
