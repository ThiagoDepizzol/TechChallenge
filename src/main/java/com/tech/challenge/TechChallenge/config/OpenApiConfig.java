package com.tech.challenge.TechChallenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    private final SwaggerDocProperties swaggerDocProperties;

    public OpenApiConfig(SwaggerDocProperties swaggerDocProperties) {
        this.swaggerDocProperties = swaggerDocProperties;
    }

    @Bean
    public OpenAPI techChallenge() {
        return new OpenAPI()
                .info(
                        new Info().title(swaggerDocProperties.getTitle())
                                .description(swaggerDocProperties.getDescription())
                                .version(swaggerDocProperties.getVersion())
                                .license(new License()
                                        .name(swaggerDocProperties.getLicenseName())
                                        .url(swaggerDocProperties.getLicenseUrl()))
                );
    }
}
