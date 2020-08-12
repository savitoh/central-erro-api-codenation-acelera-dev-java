package com.github.savitoh.centralerroapi.event;

import com.github.savitoh.centralerroapi.util.URIUtil;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Objects;

public class RecursoCriadoEvent<T extends Serializable> extends ApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_PATH_IDENTIFICADOR = "/{id}";

    private final HttpServletResponse response;

    private final T identificadorRecurso;

    private String pathIdentificadorRecurso;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, T identificadorRecurso) {
        super(source);
        this.response = response;
        this.identificadorRecurso = identificadorRecurso;
    }

    public RecursoCriadoEvent(Object source,
                              HttpServletResponse response,
                              String nomeIdentificadorRecurso,
                              T identificadorRecurso) {
        this(source, response, identificadorRecurso);
        this.pathIdentificadorRecurso = URIUtil.criarPathTemplate(nomeIdentificadorRecurso);
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public T getIdentificadorRecurso() {
        return identificadorRecurso;
    }

    public String getPathIdentificador() {
        return Objects.nonNull(this.pathIdentificadorRecurso) ? pathIdentificadorRecurso : DEFAULT_PATH_IDENTIFICADOR;
    }
}
