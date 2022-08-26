package com.sb.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sb.camp.domain.User;
import com.sb.camp.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

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
