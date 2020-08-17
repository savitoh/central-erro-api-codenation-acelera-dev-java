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
    protected ResponseEntity<ApiErrorResponsePayload> handleRecursoNaoEncontrado(RecursoNaoEncontradoException exception) {
        final var apiErrorRespondePayload = new ApiErrorResponsePayload(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiErrorRespondePayload, HttpStatus.NOT_FOUND);
    }

}
