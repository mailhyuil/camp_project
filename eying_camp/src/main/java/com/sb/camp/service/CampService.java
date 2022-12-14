package com.sb.camp.service;

import java.util.Map;

import com.sb.camp.domain.Camp;

public interface CampService {
	public Map<String, Object> getPaginationAndCampListByKeywords(String doNm, String sigunguNm, String facltNm, int page);
	
	public void insertAPI() throws Throwable;
	
	public Camp getCampById(long id);
	
	public int likeCamp(long id, String username);
}
