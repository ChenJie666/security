package com.iotmars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * spring security配置
 */
//@EnableOAuth2Sso
@EnableWebFluxSecurity
public class SecurityConfig {

	//security的鉴权排除的url列表
//	private static final String[] excludedAuthPages = {"/v1/api-menu/menu/**","/v1/api-menu/menu-anon/**"};

	@Bean
	SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {

		http.csrf().disable()
				.authorizeExchange()
				.pathMatchers("/**").permitAll()
				.anyExchange().authenticated();

//		http.oauth2ResourceServer().jwt();

		return http.build();
	}
}