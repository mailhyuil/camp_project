package com.sb.camp.service;

import org.springframework.ui.Model;

import com.sb.camp.domain.Camp;

public interface CampService {
	public void findByKeywords(Model model, String doNm, String sigunguNm, String facltNm, int page);
	public void insertAPI();
	public Camp findCampById(long id);
	public void likeCamp(long id, String username, Model model);
}
