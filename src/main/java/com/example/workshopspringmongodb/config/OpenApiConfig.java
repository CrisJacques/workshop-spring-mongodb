package com.example.workshopspringmongodb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI workshopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workshop Spring Boot e MongoDB API")
                        .description("""
                                API REST para gerenciamento de usuários e consulta de publicações.
                                Os exemplos apresentados na documentação usam datas no formato ISO 8601.
                                """)
                        .version("1.0.0"));
    }
}
