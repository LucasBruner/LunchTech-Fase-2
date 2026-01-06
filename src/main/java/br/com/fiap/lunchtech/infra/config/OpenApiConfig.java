package br.com.fiap.lunchtech.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI LunchTech() {
        return new OpenAPI().info(
                new Info().title("Lunchtech API")
                        .description("Projeto desenvolvido durante a fase 2 do curso FIAP")
                        .version("v0.0.2")
                        .license(new License().name("Apache 2.0").url("https://github.com/LucasBruner/LunchTech-Fase-2")));
    }

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("api-v1")
                .pathsToMatch("/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiV2() {
        return GroupedOpenApi.builder()
                .group("api-v2")
                .pathsToMatch("/v2/**")
                .build();
    }
}
