package com.sb.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sb.camp.repository.CampRepository;

@Controller
public class HomeController {
	
	@Autowired
	CampRepository campRepository;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		System.out.println(campRepository.findById(2L).get().getCampId());
		return null;
	}
}
