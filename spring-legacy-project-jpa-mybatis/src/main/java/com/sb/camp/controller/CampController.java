package com.sb.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sb.camp.domain.Camp;
import com.sb.camp.service.CampService;

@Controller
@RequestMapping("/camp")
public class CampController {
	
	@Autowired
	CampService campService;
	
	@GetMapping({"/",""})
	public String home() {
		return "/camp/home";
	}
	
	@PostMapping({"/",""})
	public String home(Model model, String doNm, String sigunguNm, String facltNm) {
		campService.findByKeywords(doNm, sigunguNm, facltNm);
		return "/camp/home";
	}
	
}
