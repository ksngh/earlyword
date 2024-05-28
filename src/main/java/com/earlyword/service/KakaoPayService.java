package com.earlyword.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.earlyword.dto.KakaoPayDto;

@Service
public class KakaoPayService {

	String cid = "TC0ONETIME";
	String adminKey = "f03af300ac02716ddf09b1c8dd45abf0";

	public void kakaoPayReady() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", cid);
		params.add("partner_order_id", "1234");
		params.add("partner_user_id", "1234");
		params.add("item_name", "상품명");
		params.add("quantity", "1");
		params.add("total_amount", "10000");
		params.add("vat_amount", "1000");
		params.add("tax_free_amount", "1000");
		params.add("approval_url", "http://localhost:8080/payment/success");
		params.add("cancel_url", "http://localhost:8080/payment/cancel");
		params.add("fail_url", "http://localhost:8080/payment/fail");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());

		RestTemplate restTemplate = new RestTemplate();

		KakaoPayDto.ReadyResponse readyResponse = restTemplate.postForObject(
			"https://kapi.kakao.com/v1/payment/ready",
			requestEntity,
			KakaoPayDto.ReadyResponse.class
		);
		System.out.println("readyResponse = " + readyResponse);
		// return readyResponse;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();

		String auth = "KakaoAK " + adminKey;

		httpHeaders.set("Authorization", auth);
		httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		System.out.println("auth = " + auth);
		return httpHeaders;
	}
}
