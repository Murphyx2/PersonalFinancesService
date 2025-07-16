package com.app.personalfinancesservice.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class CacheConfig {

	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig() //
				.entryTtl(Duration.ofMinutes(60)) //
				.disableCachingNullValues();

		// Domain-specific cache
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

		// Portfolios
		cacheConfigurations.put("portfolios", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofHours(1)) //
				.disableCachingNullValues() //
		);

		cacheConfigurations.put("portfoliosList", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofHours(3)) //
				.disableCachingNullValues() //
		);

		// Budgets
		cacheConfigurations.put("budgets", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofMinutes(30)) //
				.disableCachingNullValues() //
		);

		cacheConfigurations.put("budgetsList", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofMinutes(60)) //
				.disableCachingNullValues() //
		);

		// categories
		cacheConfigurations.put("categories", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofDays(7)) //
				.disableCachingNullValues() //
		);

		cacheConfigurations.put("categoriesList", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofDays(7)) //
				.disableCachingNullValues() //
		);

		// Category Planners
		cacheConfigurations.put("categoryPlanners", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofDays(3)) //
				.disableCachingNullValues() //
		);

		cacheConfigurations.put("categoryPlannerList", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofDays(3)) //
				.disableCachingNullValues() //
		);

		// Transactions
		cacheConfigurations.put("transactions", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofMinutes(30)) //
				.disableCachingNullValues() //
		);

		cacheConfigurations.put("transactionsList", RedisCacheConfiguration //
				.defaultCacheConfig() //
				.entryTtl(Duration.ofHours(1)) //
				.disableCachingNullValues() //
		);

		return RedisCacheManager.builder(redisConnectionFactory) //
				.cacheDefaults(cacheConfig) //
				.withInitialCacheConfigurations(cacheConfigurations)
				.build();
	}
}
