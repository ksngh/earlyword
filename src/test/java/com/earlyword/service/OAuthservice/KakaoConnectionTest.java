package com.earlyword.service.OAuthservice;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KakaoConnectionTest {

	@Autowired
	KakaoConnection kakaoConnection;

	@Test
	void makeURL() {
		Assertions.assertThat(kakaoConnection.makeURL())
			.isEqualTo("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=보안상 삭제&redirect_uri=http://localhost:8080/");
	}

}