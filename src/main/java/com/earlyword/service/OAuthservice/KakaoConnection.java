package com.earlyword.service.OAuthservice;


import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.net.URL;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.earlyword.dto.oauth.KakaoTokenDTO;
import com.earlyword.dto.oauth.KakaoUserDTO;
import com.earlyword.util.oauth.HttpUtil;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoConnection {

	private final HttpUtil httpUtil ;

	@Value("${kakao.client-id}")
	private String clientId;

	@Value("${kakao.redirect-uri}")
	private String redirectUri;

	@Value("${kakao.client-secret}")
	private String clientSecret;

	public String makeURL() {
		StringBuilder kakaoUrl = new StringBuilder(
			"https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=");
		kakaoUrl.append(clientId);
		kakaoUrl.append("&redirect_uri=");
		kakaoUrl.append(redirectUri);
		return kakaoUrl.toString();
	}

	public KakaoTokenDTO getToken(String code) {

		KakaoTokenDTO kakaoTokenDTO = null;

		try {
			// POST 방식으로 요청 보낼 URL 설정
			URL url = new URL("https://kauth.kakao.com/oauth/token");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");

			//출력 스트림 활성화
			conn.setDoOutput(true);

			//요청 헤더 설정
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			//요청 바디 설정
			StringJoiner bodyParam = new StringJoiner("&");

			bodyParam.add("grant_type=" + "authorization_code");
			bodyParam.add("client_id=" + clientId);
			bodyParam.add("redirect_url=" + redirectUri);
			bodyParam.add("code=" + code);
			bodyParam.add("client_secret=" + clientSecret);

			// 요청보내기
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = bodyParam.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			JsonObject jsonObject = httpUtil.KakaoHttpUtil(conn);

			// KakaoTokenDTO 생성
			kakaoTokenDTO = KakaoTokenDTO.builder()
				.accessToken(jsonObject.get("access_token").getAsString())
				.tokenType(jsonObject.get("token_type").getAsString())
				.refreshToken(jsonObject.get("refresh_token").getAsString())
				.expiresIn(jsonObject.get("expires_in").getAsInt())
				.scope(jsonObject.get("scope").getAsString())
				.refreshTokenExpiresIn(jsonObject.get("refresh_token_expires_in").getAsInt())
				.build();

			return kakaoTokenDTO;

			} catch (Exception e) {
			e.printStackTrace();
		}
		return kakaoTokenDTO;
	}

	public KakaoUserDTO getUserInfo(KakaoTokenDTO kakaoTokenDTO){
		KakaoUserDTO kakaoUserDTO = null;
		try {
			// POST 방식으로 요청 보낼 URL 설정
			URL url = new URL("https://kapi.kakao.com/v2/user/me");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");

			//출력 스트림 활성화
			conn.setDoOutput(true);

			//요청 헤더 설정
			conn.setRequestProperty("Authorization", "Bearer " + kakaoTokenDTO.getAccessToken());
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			JsonObject jsonObject = httpUtil.KakaoHttpUtil(conn);

			JsonObject kakaoAccount = jsonObject.getAsJsonObject("kakao_account");
			JsonObject profile = kakaoAccount.getAsJsonObject("profile");


			// KakaoUserDTO 생성
			kakaoUserDTO = KakaoUserDTO.builder()
				.nickname(profile.get("nickname").getAsString())
				.email(kakaoAccount.get("email").getAsString())
				.build();

			System.out.println(kakaoUserDTO.toString());
			return kakaoUserDTO;

			} catch (Exception e) {
			e.printStackTrace();
		}
		return kakaoUserDTO;
	}
}