package com.sb.camp.controller;

import java.util.Properties;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sb.camp.repository.BbsRepository;
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
	@Autowired
	BbsRepository bbsRepository;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@Autowired
	@Qualifier("api")
	Properties api;
	
	@GetMapping("/test")
	@ResponseBody
	@Transactional
	public String test() {
		System.out.println(api.get("weather.serviceKey"));
		return null;
	}
}
