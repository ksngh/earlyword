package com.earlyword.domain;

import lombok.Getter;

@Getter
public enum Role {
	USER("USER_KEY"), ADMIN("ADMIN_KEY");

	private final String key;

	Role(String key) {
		this.key = key;
	}

}
