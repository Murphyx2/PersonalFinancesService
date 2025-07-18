package com.app.personalfinancesservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO: Hardcode as testing, later implementation will use properties for the Origins
		registry.addMapping("/**") //
				.allowedOrigins("http://localhost:5173") //
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //
				.allowedHeaders("*");
	}
}
