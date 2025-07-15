package com.app.personalfinancesservice;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.cdimascio.dotenv.Dotenv;

@SuppressWarnings("squid:S2629")
@SpringBootApplication
@EntityScan(basePackages = "com.personalfinance.api.domain")
@EnableCaching
public class PersonalFinancesServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(PersonalFinancesServiceApplication.class);

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./").load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication app = new SpringApplication(PersonalFinancesServiceApplication.class);
		ConfigurableApplicationContext context = app.run(args);

		logger.info(String.format("Active profiles: %s", Arrays.toString(context.getEnvironment().getActiveProfiles())));
	}

}
