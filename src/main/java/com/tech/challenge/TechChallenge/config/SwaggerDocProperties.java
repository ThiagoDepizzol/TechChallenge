package com.tech.challenge.TechChallenge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:swagger-doc.properties")
@ConfigurationProperties(prefix = "api.doc")
@Getter
@Setter
public class SwaggerDocProperties {
    private String title;

    private String description;

    private String version;

    private String licenseName;

    private String licenseUrl;
}
