package com.github.savitoh.centralerroapi.event.listener;

import com.github.savitoh.centralerroapi.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent<Serializable>> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Serializable identificadorRecurso = recursoCriadoEvent.getIdentificadorRecurso();
        String pathIdentificadorRecurso = recursoCriadoEvent.getPathIdentificador();
        adicionarHeaderLocation(response, pathIdentificadorRecurso, identificadorRecurso);
    }

    private void adicionarHeaderLocation(HttpServletResponse response,
                                         String pathIdentificadorRecurso,
                                         Serializable identificadorRecurso) {
        URI locationValue = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(pathIdentificadorRecurso)
                .buildAndExpand(identificadorRecurso)
                .toUri();
        response.setHeader(HttpHeaders.LOCATION, locationValue.toASCIIString());
    }
}
