package com.github.savitoh.centralerroapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;

@Configuration
@Profile(value = {"docker", "h2"})
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${api.version}") String apiVersion) {
        return new OpenAPI()
                .info(new Info().title("Serviço REST Central Erro")
                        .version(apiVersion)
                        .description("Projeto Final da Aceleração Java Online da Codenation.")
                        .contact(new Contact().name("Sávio Raires de Souza").url("https://github.com/savitoh").email("savioraires10@gmail.com")))
                .servers(Collections.singletonList(new Server().url("http://localhost:8080")))
                .components(new Components()
                        .addSecuritySchemes("auth-scheme", new SecurityScheme().scheme("bearer").type(SecurityScheme.Type.HTTP).in(SecurityScheme.In.HEADER).name("auth-scheme")));
    }

}
