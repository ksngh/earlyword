package com.earlyword.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.earlyword.DTO.OAuthAttributes;
import com.earlyword.domain.Member;
import com.earlyword.domain.SessionMember;
import com.earlyword.mapper.MemberMapper;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberMapper memberMapper;
	private final HttpSession httpSession;

	public CustomOAuth2UserService(MemberMapper memberMapper, HttpSession httpSession) {
		this.memberMapper = memberMapper;
		this.httpSession = httpSession;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

		Member member = saveOrUpdate(attributes);
		httpSession.setAttribute("member", new SessionMember(member));

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
			attributes.getAttributes(),
			attributes.getNameAttributeKey()
		);
	}

	private Member saveOrUpdate(OAuthAttributes attributes) {
		Member member = memberMapper.findByEmail(attributes.getEmail())
			.map(entity -> entity.update(attributes.getName()))
			.orElse(attributes.toEntity());

		return memberMapper.save(member);

	}
}
