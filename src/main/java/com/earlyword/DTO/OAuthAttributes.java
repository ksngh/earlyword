package com.earlyword.DTO;

import java.util.Map;

import com.earlyword.domain.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String email;

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
		Map<String, Object> attributes) {
		return ofKakao("id", attributes);
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("kakao_account");
		Map<String, Object> account = (Map<String, Object>)attributes.get("profile");

		return OAuthAttributes.builder()
			.email((String)response.get("email"))
			.attributes(response)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public Member toEntity() {
		return Member.builder().email(email).build();
	}
}
