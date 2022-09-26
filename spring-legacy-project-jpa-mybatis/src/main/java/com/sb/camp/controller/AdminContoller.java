package com.sb.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminContoller {
	@GetMapping("home")
	public String home() {
		return null;
	}
}
