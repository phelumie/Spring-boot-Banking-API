package com.microfinanceBank.Transaction.Config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Slf4j
public class ResourceServerConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.cors()
				.and()
				.csrf().disable()
				.formLogin().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/actuator/**","/webjars/**","/swagger-ui*/**",
						"/v3/api-docs/**")
				.permitAll()
				.and()
			.authorizeRequests()
				.anyRequest().authenticated() // OR .access("authenticated AND hasRole('product_read')")
			.and()
				.oauth2ResourceServer()
					.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
	}

	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
		return jwtConverter;
	}
//	@Bean
//	public KeycloakConfigResolver KeycloakConfigResolver() {
//		return new KeycloakSpringBootConfigResolver();
//	}
	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}
//	@Bean
//	protected SessionRegistry buildSessionRegistry() {
//		return new SessionRegistryImpl();
//	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RequestInterceptor requestInterceptor() {

		return requestTemplate -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null) {
				var  details = (Jwt)authentication.getPrincipal();
				requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", details.getTokenValue()));
			}
		};
	}
	@Bean
	@ConditionalOnMissingBean
	public LoadBalancerClientFactory loadBalancerClientFactory(LoadBalancerClientsProperties properties){
		return new LoadBalancerClientFactory(properties){

			protected AnnotationConfigApplicationContext createContext(String name){
				ClassLoader originalClassLoader=Thread.currentThread().getContextClassLoader();
				Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
				AnnotationConfigApplicationContext context=super.createContext(name);
				Thread.currentThread().setContextClassLoader((originalClassLoader));
				return context;
			}
		};
	}

}

