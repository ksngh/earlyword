package com.earlyword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "index.html";
	}

	@GetMapping("/login")
	public String login(@RequestParam(name = "code", required = false)String code){
		return "login.html";
	}


}
