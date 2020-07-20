package com.iotmars.config;

import cn.hutool.core.convert.Converter;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * spring security配置
 */
//@EnableOAuth2Sso
@EnableWebFluxSecurity
public class SecurityConfig {

	//security的鉴权排除的url列表
//	private static final String[] excludedAuthPages = {"/v1/api-menu/menu/**","/v1/api-menu/menu-anon/**"};

	@Bean
	public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {

		http.csrf().disable().exceptionHandling().and()
				.authorizeExchange()
				.pathMatchers("/order/**").hasAuthority("ROLE_admin")
				.anyExchange().authenticated();

		http.oauth2ResourceServer().jwt();

		return http.build();
	}

//	@Bean
//	public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter(){
//		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//	}



}