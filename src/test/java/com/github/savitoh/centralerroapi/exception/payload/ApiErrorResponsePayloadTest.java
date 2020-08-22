package com.github.savitoh.centralerroapi.exception.payload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorResponsePayloadTest {

    @Test
    void deve_instanciar_ApiErrorResponsePayload_construtor_mensagem_e_statusHttp() {
        final String mensagem = "erro";
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiErrorResponsePayload apiErrorResponsePayload = new ApiErrorResponsePayload(mensagem, httpStatus);

        Assertions.assertNotNull(apiErrorResponsePayload);
        Assertions.assertEquals(mensagem, apiErrorResponsePayload.getMensagem());
        Assertions.assertEquals(httpStatus.getReasonPhrase(), apiErrorResponsePayload.getStatusHttp());
        Assertions.assertEquals(httpStatus.value(), apiErrorResponsePayload.getCodigoHttp());
    }

    @Test
    void deve_subir_exception_quando_instanciar_ApiErrorResponsePayload_passando_status_nullo() {
        final String mensagem = "erro";
        final HttpStatus httpStatus = null;

        Assertions.assertThrows(IllegalArgumentException.class, ()-> new ApiErrorResponsePayload(mensagem, httpStatus));
    }

}