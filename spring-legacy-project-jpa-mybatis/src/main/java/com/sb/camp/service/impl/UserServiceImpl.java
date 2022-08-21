package com.sb.camp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.camp.domain.User;
import com.sb.camp.persistence.UserDao;
import com.sb.camp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public int saveUser(User user) {
			userDao.insert(user);
		return 0;
	}

}
