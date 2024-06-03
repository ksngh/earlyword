package com.earlyword.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.earlyword.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;

	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws
		Exception {
		http.csrf()
			.disable()
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin()
				// .formLogin(formLogin -> formLogin
				// 	.loginPage("/login") // 사용자 정의 로그인 페이지 경로 설정
				// 	.defaultSuccessUrl("/index")) // 로그인 성공 시 리다이렉트 경로 설정
				// .failureUrl("/your-login-path?error=true") // 로그인 실패 시 리다이렉트 경로 설정)
			.disable()
			.httpBasic()
			.disable()
			.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests.requestMatchers(new MvcRequestMatcher(introspector, "/api/user"))
					.permitAll()
					.anyRequest()
					.permitAll())
					// .authenticated())
			.oauth2Login(oauth2Login -> oauth2Login.userInfoEndpoint(
				userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)));

		return http.build();
	}
}