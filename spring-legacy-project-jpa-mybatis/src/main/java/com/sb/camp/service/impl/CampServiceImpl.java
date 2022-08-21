package com.sb.camp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.camp.domain.Camp;
import com.sb.camp.persistence.CampDao;
import com.sb.camp.service.CampService;

@Service
public class CampServiceImpl implements CampService{
	
	@Autowired
	private CampDao campDao;
	
	@Override
	public void insertAPI() {
		// api로 json데이터를 받아서 DB에 insert
		// DB에 json데이터가 들어있을시에는 업데이트
		campDao.insertAPI();
	}

}
