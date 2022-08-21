package com.sb.camp.service;

import java.util.List;

import com.sb.camp.domain.Camp;

public interface CampService {
	public List<Camp> findByKeywords(String doNm, String sigunguNm, String facltNm);
	public void insertAPI();
}
