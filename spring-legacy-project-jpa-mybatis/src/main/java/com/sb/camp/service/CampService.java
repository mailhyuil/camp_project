package com.sb.camp.service;

import java.util.Map;

import com.sb.camp.domain.Camp;

public interface CampService {
	public Map<String, Object> getPaginationAndCampListByKeywords(String doNm, String sigunguNm, String facltNm, int page);
	
	public void insertAPI();
	
	public Camp getCampById(long id);
	
	public void likeCamp(long id, String username);
}
