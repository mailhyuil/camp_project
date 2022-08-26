package com.sb.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sb.camp.domain.User;
import com.sb.camp.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("join")
	public String join() {
		return null;
	}

	@PostMapping("join")
	public String join(User user){
		userService.join(user);
		return "redirect:/";
	}
	
	@GetMapping("login")
	public String login() {
		return null;
	}
}
