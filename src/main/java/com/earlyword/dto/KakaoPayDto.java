package com.earlyword.dto;

import lombok.Setter;
import lombok.ToString;

public class KakaoPayDto {

	@Setter
	@ToString
	public static class ReadyResponse {
		private String tid;
		private String next_redirect_mobile_url;
		private String next_redirect_pc_url;
		private String created_at;
	}


}
