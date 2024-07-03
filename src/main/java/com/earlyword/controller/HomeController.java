package com.earlyword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Getter;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(){
		return "/index";
	}


}
