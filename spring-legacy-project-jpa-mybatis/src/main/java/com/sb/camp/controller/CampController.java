package com.sb.camp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sb.camp.domain.Camp;
import com.sb.camp.service.CampService;
import com.sb.camp.service.WeatherService;

@Controller
@RequestMapping("/camp")
public class CampController {
	
	@Autowired
	CampService campService;
	@Autowired
	WeatherService weatherService;
	
	@GetMapping({"/",""})
	public String home(Model model, String doNm, String sigunguNm, String facltNm,
			@RequestParam(required = false, defaultValue = "1") int page) {
		campService.findByKeywords(model, doNm, sigunguNm, facltNm, page);
		return "/camp/home";
	}
	
	@GetMapping("/detail")
	public String detail(Model model, String id) {
		Camp camp = campService.findCampById(id);
		weatherService.getWeatherByLatAndLon(model, camp.getMapY(), camp.getMapX());
		model.addAttribute("CAMP", campService.findCampById(id));
		return "/camp/detail";
	}
	
	@ResponseBody
	@GetMapping("/getLatAndLon")
	public ResponseEntity<Map<String,String>> getLatAndLon(String id) {
		Camp camp = campService.findCampById(id);
		Map<String, String> latlon = new HashMap<>();
		latlon.put("lat", camp.getMapY());
		latlon.put("lon", camp.getMapX());
		return ResponseEntity
                .ok(latlon);
	}
	
}
