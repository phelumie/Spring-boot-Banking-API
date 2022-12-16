package com.microfinanceBank.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
				.csrf().disable()
				.cors()
				.and()
				.formLogin().disable()
//				.headers(headerSpec -> headerSpec.contentSecurityPolicy("script-src 'self'"))
				.authorizeExchange()
				.pathMatchers("/actuator/**","/customer/actuator/**","/issue/actuator/**","/transaction/actuator/**",
						"/employee/actuator/**","/loan/actuator/**")
				.permitAll()
				.and()
	            .authorizeExchange()
				.pathMatchers("/webjars/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**",
						"/swagger-ui.html","/customer/v3/api-docs/**","/transaction/v3/api-docs/**",
						"/employee/v3/api-docs/**","/issue/v3/api-docs/**","/loan/v3/api-docs/**")
				.permitAll()
				.and()
				.authorizeExchange()
				.anyExchange()
				.authenticated()
				.and()
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.oauth2ResourceServer()
				.jwt();

		return http.build();

	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
	}
	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}
}
