package com.app.personalfinancesservice.config;

import java.time.Duration;

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

		return RedisCacheManager.builder(redisConnectionFactory) //
				.cacheDefaults(cacheConfig) //
				.build();
	}
}
