package com.microfinanceBank.apigateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cache")
@Configuration
@Data
public class PropertiesConfiguration {
	private String redisHost;


}
