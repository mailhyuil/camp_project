package com.sb.camp.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sb.camp.domain.User;
import com.sb.camp.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("home")
	public String home(Model model, Principal principal,
			@RequestParam(required = false, defaultValue = "1") int bbs_page,
			@RequestParam(required = false, defaultValue = "1") int campLike_page) {
		model.addAllAttributes(userService.findPaginatedBbsListByUsername(principal.getName(), bbs_page));
		model.addAllAttributes(userService.findPaginatedCampLikeLikeByUsername(principal.getName(), campLike_page));
		return null;
	}

	@GetMapping("join")
	public String join(@ModelAttribute("user") User user) {
		return null;
	}

	@PostMapping("join")
	public String join(@Valid User user, BindingResult result) {

		if (result.hasErrors()) {
			return "/user/join";
		}

		if (!user.getPassword().equals(user.getRe_password())) {
			result.addError(new FieldError("User", "re_password", "비밀번호가 일치하지 않습니다."));
			return "/user/join";
		}

		int ret = userService.join(user);

		if (ret == -1) {
			result.addError(new FieldError("User", "username", "이미 존재하는 아이디입니다."));
			return "/user/join";
		}

		return "redirect:/user/login";
	}

	@GetMapping("login")
	public String login(@ModelAttribute("user") User user) {
		return null;
	}

}
