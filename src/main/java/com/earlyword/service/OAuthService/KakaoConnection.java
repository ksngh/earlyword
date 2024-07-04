package com.earlyword.service.OAuthService;


import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
public class KakaoConnection {

	@Value("${kakao.client-id}")
	private String clientId;

	@Value("${kakao.redirect-uri}")
	private String redirectUri;

	public String makeURL() {
		StringBuilder kakaoUrl = new StringBuilder("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=");
		kakaoUrl.append(clientId);
		kakaoUrl.append("&redirect_uri=");
		kakaoUrl.append(redirectUri);
		return kakaoUrl.toString();
	}

}
