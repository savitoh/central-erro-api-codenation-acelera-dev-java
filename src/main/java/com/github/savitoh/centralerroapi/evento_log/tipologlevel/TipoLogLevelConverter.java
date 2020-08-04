package com.github.savitoh.centralerroapi.evento_log.tipologlevel;

import javax.persistence.AttributeConverter;

public class TipoLogLevelConverter implements AttributeConverter<TipoLogLevel, Short> {

    @Override
    public Short convertToDatabaseColumn(TipoLogLevel attribute) {
        return attribute.getId();
    }

    @Override
    public TipoLogLevel convertToEntityAttribute(Short dbData) {
        return TipoLogLevel.getById(dbData).orElseThrow(IllegalArgumentException::new);
    }
    
}