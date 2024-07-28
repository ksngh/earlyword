package com.earlyword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.earlyword.dto.oauth.KakaoTokenDTO;
import com.earlyword.dto.oauth.KakaoUserDTO;
import com.earlyword.service.MemberService;
import com.earlyword.service.OAuthservice.KakaoConnection;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final KakaoConnection kakaoConnection;
	private final MemberService memberService;

	@GetMapping("/login")
	String login() {
		return "redirect:"
			.concat(kakaoConnection.makeURL());
	}

	@GetMapping("/oauth/kakao/callback/")
	String callback(@RequestParam("code") String code) {
		KakaoTokenDTO kakaoTokenDTO = kakaoConnection.getToken(code);
		KakaoUserDTO kakaoUserDTO = kakaoConnection.getUserInfo(kakaoTokenDTO);
		// memberService.saveOrUpdateMember(kakaoUserDTO);
		return "redirect:/";
	}

}
