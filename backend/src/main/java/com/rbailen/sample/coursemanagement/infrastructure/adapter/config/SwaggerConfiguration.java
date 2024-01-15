package com.rbailen.sample.coursemanagement.infrastructure.adapter.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components())
                .info(
                        new Info()
                                .title("COURSE MANAGEMENT API")
                                .description("API that will handle courses management")
                                .version("1.0")
                .contact(
                        new Contact()
                                .name("rbailen")
                                .url("https://rbailen.github.io")
                ));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("CourseManagementAPI")
                .pathsToMatch("/v1/**")
                .build();
    }

}