package com.microfinanceBank.Customer.Config;

import jdk.jfr.Name;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;

@ConfigurationProperties(prefix = "keycloak")
@Configuration
@Data
@RefreshScope
public class KeycloakConfiguration {

	@Value("${keycloak.auth-server-url}")
	private String serverURL;
	private String realm;
	private String resource;
	@Value("${keycloak.credentials.secret}")
//	@Name("credentials.secret")
	private String clientSecret;




}
