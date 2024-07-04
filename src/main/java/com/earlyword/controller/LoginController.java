package com.earlyword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.earlyword.service.OAuthService.KakaoConnection;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final KakaoConnection kakaoConnection;

	@GetMapping("/login")
	String login(){
		return "redirect:"
			.concat(kakaoConnection.makeURL());
	}

	// @GetMapping("/oauth/kakao/callback/")
	// String callback(){
	//
	// }
	//
	// @PostMapping("/oauth/kakao/callback/")
	// String getToken(){
	//
	// }

}
