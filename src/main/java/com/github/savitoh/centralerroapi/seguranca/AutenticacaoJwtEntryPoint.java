package com.github.savitoh.centralerroapi.seguranca;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.savitoh.centralerroapi.exception.payload.ApiErrorResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoJwtEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoJwtEntryPoint.class);

    private final ObjectMapper objectMapper;

    public AutenticacaoJwtEntryPoint() {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.error("Um acesso não autorizado foi verificado. Mensagem: {}", e.getMessage());
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        final var apiErrorResponsePayload = new ApiErrorResponsePayload(e.getMessage(), HttpStatus.UNAUTHORIZED);
        httpServletResponse.getOutputStream()
                .println(objectMapper.writeValueAsString(apiErrorResponsePayload));
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

}
