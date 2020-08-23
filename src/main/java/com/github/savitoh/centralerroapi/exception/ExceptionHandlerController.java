package com.github.savitoh.centralerroapi.exception;

import com.github.savitoh.centralerroapi.exception.payload.ApiErrorResponsePayload;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String MESANGEM_VALIDACAO_CAMPOS = "Houve falha na validação do(s) campo(s) da requisição.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final var apiErrorResponsePayload = new ApiErrorResponsePayload(MESANGEM_VALIDACAO_CAMPOS, status, ex);
        return new ResponseEntity<>(apiErrorResponsePayload, status);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    protected ResponseEntity<ApiErrorResponsePayload> handleRecursoNaoEncontrado(
            RecursoNaoEncontradoException exception) {
        final var apiErrorResponsePayload = new ApiErrorResponsePayload(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponsePayload, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiErrorResponsePayload> handlerConstraintViolantionException(
            ConstraintViolationException constraintViolationException) {
        final var apiErrorResponsePayload =
                new ApiErrorResponsePayload(HttpStatus.BAD_REQUEST, constraintViolationException);
        return new ResponseEntity<>(apiErrorResponsePayload, HttpStatus.BAD_REQUEST);
    }

}
