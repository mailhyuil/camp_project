package com.sb.camp.service;

import java.util.List;

import org.springframework.ui.Model;

import com.sb.camp.domain.Camp;

public interface CampService {
	public void findByKeywords(Model model, String doNm, String sigunguNm, String facltNm, int page);
	public void insertAPI();
	public Camp findCampById(String id);
	public void increaseLike(String id);
}
