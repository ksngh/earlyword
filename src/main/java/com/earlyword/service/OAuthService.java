package com.earlyword.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.earlyword.domain.KakaoInfo;
import com.earlyword.domain.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OAuthService {

	@Autowired
	MemberService memberService;

	@Value("${kakao.client.id}")
	String clientId;
	@Value("${kakao.redirect.uri}")
	String redirectUri;
	@Value("${kakao.client.secret}")
	String clientSecret;


	public String KakaoConnectionUrl(){
		StringBuilder url = new StringBuilder();
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("client_id=").append(clientId);
		url.append("&redirect_uri=").append(redirectUri);
		url.append("&response_type=code");

		return url.toString();
	}

	// public String getAccessToken(String code){
	//
	// }

	// public String getAccessToken(String code) throws JsonProcessingException {
	// 	// HTTP Header 생성
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	//
	// 	// HTTP Body 생성
	// 	MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	// 	body.add("grant_type", "authorization_code");
	// 	body.add("client_id", clientId);
	// 	body.add("redirect_uri", redirectUri);
	// 	body.add("code", code);
	// 	body.add("client_secret", clientSecret);
	//
	// 	// HTTP 요청 보내기
	// 	HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
	// 	RestTemplate rt = new RestTemplate();
	// 	ResponseEntity<String> response = rt.exchange(
	// 		"https://kauth.kakao.com/oauth/token",
	// 		HttpMethod.POST,
	// 		kakaoTokenRequest,
	// 		String.class
	// 	);
	//
	// 	// HTTP 응답 (JSON) -> 액세스 토큰 파싱
	// 	String responseBody = response.getBody();
	// 	ObjectMapper objectMapper = new ObjectMapper();
	// 	JsonNode jsonNode = objectMapper.readTree(responseBody);
	//
	// 	return jsonNode.get("access_token").asText();
	// }
	//
	// public KakaoInfo getKakaoInfo(String accessToken) throws JsonProcessingException {
	// 	// HTTP Header 생성
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.add("Authorization", "Bearer " + accessToken);
	// 	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	//
	// 	// HTTP 요청 보내기
	// 	HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
	// 	RestTemplate rt = new RestTemplate();
	// 	ResponseEntity<String> response = rt.exchange(
	// 		"https://kapi.kakao.com/v2/user/me",
	// 		HttpMethod.POST,
	// 		kakaoUserInfoRequest,
	// 		String.class
	// 	);
	//
	// 	// responseBody에 있는 정보 꺼내기
	// 	String responseBody = response.getBody();
	// 	ObjectMapper objectMapper = new ObjectMapper();
	// 	JsonNode jsonNode = objectMapper.readTree(responseBody);
	//
	// 	Long id = jsonNode.get("id").asLong();
	// 	String email = jsonNode.get("kakao_account").get("email").asText();
	// 	String nickname = jsonNode.get("properties")
	// 		.get("nickname").asText();
	//
	// 	return new KakaoInfo(id, nickname, email);
	// }
	//
	// public Member ifNeedKakaoInfo (KakaoInfo kakaoInfo) {
	// 	// DB에 중복되는 email이 있는지 확인
	// 	String kakaoEmail = kakaoInfo.getEmail();
	// 	Member kakaoMember = memberService.findMemberEmail(kakaoEmail);
	//
	// 	// 회원가입
	// 	if (kakaoMember == null) {
	// 		String kakaoNickname = kakaoInfo.getNickname();
	// 		// 이메일로 임시 id 발급
	// 		int idx= kakaoEmail.indexOf("@");
	// 		String kakaoId = kakaoEmail.substring(0, idx);
	// 		// 임시 password 발급 - random UUID
	// 		String tempPassword = UUID.randomUUID().toString();
	//
	// 		RegisterRequest registerMember = new RegisterRequest();
	// 		registerMember.setId(kakaoId);
	// 		registerMember.setPassword(tempPassword);
	// 		registerMember.setNickname(kakaoNickname);
	// 		registerMember.setEmail(kakaoEmail);
	//
	// 		memberService.saveMember(registerMember);
	// 		// DB 재조회
	// 		kakaoMember = memberService.findMemberEmail(kakaoEmail);
	// 	}
	//
	// 	return kakaoMember;
	// }
}
