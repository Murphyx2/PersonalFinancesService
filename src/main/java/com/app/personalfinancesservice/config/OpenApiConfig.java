package com.app.personalfinancesservice.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info( //
		title = "Personal Finance API", //
		version = "1.0.0",
		description = "API for managing personal finance and budgets"
	)
)
public class OpenApiConfig {
}
