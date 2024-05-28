package com.earlyword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.earlyword.service.KakaoPayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;

	@PostMapping("/ready")
	public void readytToKakaoPay() {
		kakaoPayService.kakaoPayReady();
	}
}
