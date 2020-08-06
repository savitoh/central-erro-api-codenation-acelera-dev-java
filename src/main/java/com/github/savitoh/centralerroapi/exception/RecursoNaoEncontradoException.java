package com.github.savitoh.centralerroapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException{

    private static final String MENSAGEM_PADRAO = "Recurso não encontrado";

    public RecursoNaoEncontradoException() {
        super(MENSAGEM_PADRAO);
    }

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
