package com.microfinanceBank.Loan.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Slf4j
@org.keycloak.adapters.springsecurity.KeycloakConfiguration
@Import(KeycloakSpringBootConfigResolver.class)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
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
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		KeycloakAuthenticationProvider authenticationProvider = new KeycloakAuthenticationProvider();
//		authenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//		auth.authenticationProvider(authenticationProvider);
//	}

//	@Autowired

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

}

