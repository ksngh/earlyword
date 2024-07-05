package com.earlyword.service.OAuthService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.earlyword.dto.oauth.KakaoTokenDTO;
import com.earlyword.dto.oauth.KakaoUserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class KakaoConnection {

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

			// 응답 코드가 200 (HTTP_OK) 인지 확인
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// 입력 스트림 생성
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				// 응답 내용을 한 줄씩 읽어서 StringBuffer에 추가
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				// 입력 스트림 닫기
				in.close();

				// Gson 객체 생성
				Gson gson = new Gson();
				// JSON 문자열을 JsonObject로 변환
				JsonObject jsonObject = gson.fromJson(String.valueOf(response), JsonObject.class);

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

			} else {
				System.out.println("POST request not worked");
			}
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

			// 응답 코드가 200 (HTTP_OK) 인지 확인
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// 입력 스트림 생성
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				// 응답 내용을 한 줄씩 읽어서 StringBuffer에 추가
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				// 입력 스트림 닫기
				in.close();

				// Gson 객체 생성
				Gson gson = new Gson();
				// JSON 문자열을 JsonObject로 변환
				JsonObject jsonObject = gson.fromJson(String.valueOf(response), JsonObject.class);
				System.out.println(jsonObject);

				JsonObject kakaoAccount = jsonObject.getAsJsonObject("kakao_account");
				JsonObject profile = kakaoAccount.getAsJsonObject("profile");


				// KakaoUserDTO 생성
				kakaoUserDTO = KakaoUserDTO.builder()
					.profileNickname(profile.get("nickname").getAsString())
					.accountEmail(kakaoAccount.get("email").getAsString())
					.build();

				System.out.println(kakaoUserDTO.toString());

				return kakaoUserDTO;

			} else {
				System.out.println("POST request not worked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kakaoUserDTO;
	}
}