package com.earlyword.domain;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionMember implements Serializable {

	private final String email;

	public SessionMember(Member member) {
		this.email = member.getEmail();
	}

}