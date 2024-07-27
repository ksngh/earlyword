package com.earlyword.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import com.earlyword.domain.Question;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuestionService {

	private final ObjectMapper objectMapper;

	public QuestionService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public List<Question> getQuestions() throws IOException {
		return objectMapper.readValue(new ClassPathResource("content/question_cpa.json").getFile(), new TypeReference<List<Question>>() {});
	}
}