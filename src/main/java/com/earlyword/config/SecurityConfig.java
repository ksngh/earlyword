package com.earlyword.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
			.authorizeHttpRequests()
			.requestMatchers("/")
			.permitAll()
			.anyRequest()
			.permitAll();

		// 사이트 위변조 요청 방지
		http.csrf().disable();

		// 로그인 설정
		// http.formLogin()
		// 	.loginPage("/user2/login")
		// 	.defaultSuccessUrl("/user2/loginSuccess")
		// 	.failureUrl("/user2/login?success=100)")
		// 	.usernameParameter("uid")
		// 	.passwordParameter("pass");
		//
		// // 로그아웃 설정
		// http.logout()
		// 	.invalidateHttpSession(true)
		// 	.logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
		// 	.logoutSuccessUrl("/user2/login?success=200");


		return http.build();
	}

	@Bean
	public PasswordEncoder PasswordEncoder () {
		//return new MessageDigestPasswordEncoder("SHA-256");
		return new BCryptPasswordEncoder();
	}
}