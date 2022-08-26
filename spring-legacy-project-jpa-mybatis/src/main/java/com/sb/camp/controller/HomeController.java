package com.sb.camp.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Camp;
import com.sb.camp.domain.User;
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
	
	@GetMapping("/test")
	@ResponseBody
	@Transactional
	public String test() {
		User user = userRepository.findOneByUsername("sb");
		Camp camp = campRepository.findById(1l).get();
		bbsRepository.save(Bbs.builder().content("1234").camp(camp).user(user).title("asdf").build());
		return null;
	}
}
