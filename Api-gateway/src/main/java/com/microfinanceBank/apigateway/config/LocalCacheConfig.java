package com.microfinanceBank.apigateway.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;

@Configuration
@Profile("localCache")
public class LocalCacheConfig {

	@Bean
	public CacheManager cacheManager(){
		ConcurrentMapCacheManager cacheManager=new ConcurrentMapCacheManager();
		cacheManager.setAllowNullValues(false);
		cacheManager.setCacheNames(Collections.singleton("MyCache"));
		return cacheManager;
	}



}

