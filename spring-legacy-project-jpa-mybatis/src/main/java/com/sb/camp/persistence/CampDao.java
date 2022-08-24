package com.sb.camp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sb.camp.domain.Camp;

public interface CampDao extends GenericDao<Camp, Long> {
	public void insertAPI(List<Camp> json);
	public int getCampListCnt(@Param(value = "doNm") String doNm,
			@Param(value = "sigunguNm") String sigunguNm,
			@Param(value = "facltNm") String facltNm);
	
	public List<Camp> getCampList(@Param(value = "doNm") String doNm,
			@Param(value = "sigunguNm") String sigunguNm,
			@Param(value = "facltNm") String facltNm,
			@Param(value = "cri") int cri,
			@Param(value = "LIST_SIZE") int LIST_SIZE);
}
