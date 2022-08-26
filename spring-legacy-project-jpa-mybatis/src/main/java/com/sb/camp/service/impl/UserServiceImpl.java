package com.sb.camp.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sb.camp.domain.User;
import com.sb.camp.persistence.UserDao;
import com.sb.camp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public int join(User user) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userDao.insert(user);
		return 0;
	}

}
