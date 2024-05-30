package com.earlyword.domain;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;

@Getter
public class SessionMember implements Serializable {

	private String email;

	public SessionMember(Member member) {
		this.email = member.getEmail();
	}

}