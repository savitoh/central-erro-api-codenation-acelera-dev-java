package com.github.savitoh.centralerroapi.exception.payload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorResponsePayloadTest {

    @Test
    void test_criacao_ApiErrorResponsePayload() {
        final String mensagem = "erro";
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiErrorResponsePayload apiErrorResponsePayload = new ApiErrorResponsePayload(mensagem, httpStatus);

        Assertions.assertNotNull(apiErrorResponsePayload);
        Assertions.assertEquals(mensagem, apiErrorResponsePayload.getMensagem());
        Assertions.assertEquals(httpStatus.getReasonPhrase(), apiErrorResponsePayload.getStatus());
        Assertions.assertEquals(httpStatus.value(), apiErrorResponsePayload.getCode());
    }

    @Test
    void test_criacao_ApiErrorResponsePayload_sobe_exception_quando_status_nullo() {
        final String mensagem = "erro";
        final HttpStatus httpStatus = null;

        Assertions.assertThrows(IllegalArgumentException.class, ()-> new ApiErrorResponsePayload(mensagem, httpStatus));
    }

}