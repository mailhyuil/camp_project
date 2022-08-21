package com.sb.camp.persistence;

import java.util.List;

import com.sb.camp.domain.Camp;

public interface CampDao extends GenericDao<Camp, String> {
	public void insertAPI(List<Camp> json);
}
