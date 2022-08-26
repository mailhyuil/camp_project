package com.sb.camp.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping({ "/", "" })
	public String home(Model model, String doNm, String sigunguNm, String facltNm,
			@RequestParam(required = false, defaultValue = "1") int page) {
		model.addAllAttributes(campService.getPaginationAndCampListByKeywords(doNm, sigunguNm, facltNm, page));
		return "/camp/home";
	}

	@GetMapping("/detail")
	public String detail(Model model, long id) {
		Camp camp = campService.getCampById(id);
		model.addAllAttributes(weatherService.getWeatherByLatAndLon(camp.getMapY(), camp.getMapX()));
		model.addAttribute("CAMP", campService.getCampById(id));
		return "/camp/detail";
	}

	@ResponseBody
	@GetMapping("/getLatAndLon")
	public ResponseEntity<Map<String, String>> getLatAndLon(long id) {
		Camp camp = campService.getCampById(id);
		Map<String, String> latlon = new HashMap<>();
		latlon.put("lat", camp.getMapY());
		latlon.put("lon", camp.getMapX());
		return ResponseEntity.ok(latlon);
	}

	@GetMapping("/like/{id}")
	public String like(@PathVariable("id") long id, Principal principal) {
		campService.likeCamp(id, principal.getName());
		return "redirect:/camp/detail?id=" + id;
	}
}
