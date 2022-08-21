package com.sb.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sb.camp.service.CampService;

@Controller
@RequestMapping("/camp")
public class CampController {
	
	@Autowired
	CampService campService;
	
	@GetMapping({"/",""})
	public String home(Model model, String doNm, String sigunguNm, String facltNm,
			@RequestParam(required = false, defaultValue = "1") int page) {
		campService.findByKeywords(model, doNm, sigunguNm, facltNm, page);
		return "/camp/home";
	}
	
	@GetMapping("/detail")
	public String home(Model model, String id) {
		model.addAttribute("CAMP", campService.findCampById(id));
		return "/camp/detail";
	}
	
}
