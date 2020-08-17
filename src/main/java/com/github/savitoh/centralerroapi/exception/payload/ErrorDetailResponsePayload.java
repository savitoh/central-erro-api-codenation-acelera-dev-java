package com.github.savitoh.centralerroapi.exception.payload;

import io.jsonwebtoken.lang.Assert;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

class ErrorDetailResponsePayload {

    private final String dominio;

    private final String campo;

    private final String motivo;

    public ErrorDetailResponsePayload(String dominio, String campo, String motivo) {
        this.dominio = dominio;
        this.campo = campo;
        this.motivo = motivo;
    }

    public String getDominio() {
        return dominio;
    }

    public String getCampo() {
        return campo;
    }

    public String getMotivo() {
        return motivo;
    }

    static List<ErrorDetailResponsePayload> of(@NonNull MethodArgumentNotValidException ex) {
        Assert.notNull(ex, "Exception MethodArgumentNotValidException não pode ser nula");
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorDetailResponsePayload(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
