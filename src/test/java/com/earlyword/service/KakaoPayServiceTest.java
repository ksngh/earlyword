package com.earlyword.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KakaoPayServiceTest {

	@Autowired
	private KakaoPayService kakaoPayService;

	@Test
	void test() {
		kakaoPayService.kakaoPayReady();
	}

}