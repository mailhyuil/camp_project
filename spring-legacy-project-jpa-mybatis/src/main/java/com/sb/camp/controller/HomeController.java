package com.sb.camp.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sb.camp.domain.Camp;
import com.sb.camp.domain.CampLike;
import com.sb.camp.domain.User;
import com.sb.camp.repository.CampLikeRepository;
import com.sb.camp.repository.CampRepository;
import com.sb.camp.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	CampRepository campRepository;
	@Autowired
	CampLikeRepository campLikeRepository;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/test")
	@ResponseBody
	@Transactional
	public String test() {
		return null;
	}
}
