package com.github.savitoh.centralerroapi.exception.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;

public class ApiErrorResponsePayload {

    private final String mensagem;

    private final int codigoHttp;

    private final String statusHttp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ErrorDetailResponsePayload> errors;

    public ApiErrorResponsePayload(String mensagem, HttpStatus httpStatus, @NonNull MethodArgumentNotValidException ex) {
        this.mensagem = mensagem;
        this.codigoHttp = httpStatus.value();
        this.statusHttp = httpStatus.getReasonPhrase();
        this.errors = ErrorDetailResponsePayload.of(ex);
    }

    public ApiErrorResponsePayload(String mensagem, @NonNull HttpStatus httpStatus) {
        Assert.notNull(httpStatus, "httpStatus não pode ser nullo");
        this.mensagem = mensagem;
        this.codigoHttp = httpStatus.value();
        this.statusHttp = httpStatus.getReasonPhrase();
        this.errors = Collections.emptyList();
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getCodigoHttp() {
        return codigoHttp;
    }

    public String getStatusHttp() {
        return statusHttp;
    }

    public List<ErrorDetailResponsePayload> getErrors() {
        return errors;
    }
}
