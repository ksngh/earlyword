package com.earlyword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.earlyword.domain.Question;
import com.earlyword.service.QuestionService;

@Controller
public class QuestionController {

	private final QuestionService questionService;

	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping("/")
	public String getQuestions(Model model) throws IOException {
		List<Question> questions = questionService.getQuestions();
		model.addAttribute("questions", questions);

		return "quiz";
	}

	@PostMapping("/submit")
	public String submitAnswers(@RequestParam Map<String, String> allParams, Model model) throws IOException {
		List<Question> questions = questionService.getQuestions();

		// answers라는 파라미터를 Map에서 추출
		List<String> answers = new ArrayList<>();
		for (String key : allParams.keySet()) {
			if (key.startsWith("answers[")) {
				answers.add(allParams.get(key));
			}
		}

		// Debugging to check received answers
		System.out.println("Received answers: " + answers);

		// 아래와 같이 정답 개수 세기
		int correctCount = 0;
		for (int i = 0; i < questions.size(); i++) {
			if (i < answers.size() && questions.get(i).getCorrectAnswer().equals(answers.get(i))) {
				correctCount++;
			}
		}

		// 모델에 데이터 추가
		model.addAttribute("correctCount", correctCount);
		model.addAttribute("totalQuestions", questions.size());

		return "result"; // 결과 페이지로 전송
	}
}