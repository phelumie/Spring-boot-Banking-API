package com.microfinanceBank.Transaction.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "keycloak")
@Configuration
@Data
public class KeycloakConfiguration {

	@Value("${keycloak.auth-server-url}")
	private String serverURL;
	private String realm;
	private String resource;
	@Value("${keycloak.credentials.secret}")
	private String clientSecret;




}
