package com.sb.camp.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String join(@ModelAttribute("user") User user) {
		return null;
	}

	@PostMapping("join")
	public String join(@Valid User user, BindingResult result){
		
		if(result.hasErrors()) {
			return "/user/join";
		}
		
		int ret = userService.join(user);
		
		if(!user.getPassword().equals(user.getRe_password())) {
			result.addError(new FieldError("User", "re_password", "비밀번호가 일치하지 않습니다."));
			return "/user/join";
		}
		
		if(ret == -1) {
			result.addError(new FieldError("User", "username", "이미 존재하는 아이디입니다."));
			return "/user/join";
		}

		return "redirect:/";
	}
	
	@GetMapping("login")
	public String login(@ModelAttribute("user") User user) {
		return null;
	}
}
