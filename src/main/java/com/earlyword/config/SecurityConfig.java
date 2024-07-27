package com.earlyword.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			// CSRF 보호 비활성화
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/", "/quiz", "/quiz/submit").permitAll() // 특정 경로 접근 허용
					.anyRequest().permitAll()); // 나머지 요청은 인증 필요

		return http.build();
	}

	@Bean
	public PasswordEncoder PasswordEncoder () {
		//return new MessageDigestPasswordEncoder("SHA-256");
		return new BCryptPasswordEncoder();
	}
}