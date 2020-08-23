package com.github.savitoh.centralerroapi.exception.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public ApiErrorResponsePayload(@NonNull HttpStatus httpStatus,
                                   @NonNull ConstraintViolationException constraintViolationException) {
        Assert.notNull(httpStatus, "httpStatus não pode ser nullo");
        Assert.notNull(constraintViolationException, "constraintViolationException não pode ser nullo");
        this.mensagem = constraintViolationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.joining(", "));
        this.codigoHttp = httpStatus.value();
        this.statusHttp = httpStatus.getReasonPhrase();
        this.errors = Collections.emptyList();
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
