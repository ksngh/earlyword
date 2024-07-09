package com.earlyword.util.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.earlyword.dto.oauth.KakaoUserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.NoArgsConstructor;

@Component
public class HttpUtil {

	//요청해서 Json으로 응답을 받기
	public JsonObject KakaoHttpUtil(HttpURLConnection conn) throws IOException {

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
			return jsonObject;
		} else {
			System.out.println("POST request not worked");
			return new JsonObject();
		}
	}
}
