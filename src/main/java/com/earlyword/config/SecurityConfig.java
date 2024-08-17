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
public class SecurityConfig {


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 인가(접근권한) 설정
		http
			.authorizeHttpRequests((authorizeRequests)->
				authorizeRequests.anyRequest().permitAll());

		// 사이트 위변조 요청 방지
		// http.csrf().disable();


		return http.build();
	}

	@Bean
	public PasswordEncoder PasswordEncoder () {
		//return new MessageDigestPasswordEncoder("SHA-256");
		return new BCryptPasswordEncoder();
	}
}