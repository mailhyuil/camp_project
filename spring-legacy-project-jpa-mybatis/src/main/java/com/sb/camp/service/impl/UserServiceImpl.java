package com.sb.camp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sb.camp.domain.Authority;
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
	public int join(User user) { // MyBatis
		List<Authority> authList = new ArrayList<>();
		
		if (userDao.selectAll().isEmpty()) {
			Authority auth = new Authority();
			auth.setUser(user);
			auth.setAuthority("ROLE_ADMIN");
			authList.add(auth);
		}
		
		Authority auth = new Authority();
		auth.setAuthority("ROLE_USER");
		auth.setUser(user);
		authList.add(auth);

		if (userDao.findById(user.getUsername()) == null) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userDao.insert(user);
			userDao.insertRole(authList);
		} else {
			return -1;
		}
		return 0;
	}

	@Override
	public User findUser(String username) { // MyBatis
		return userDao.findById(username);
	}

	@Override
	public List<Authority> findAuths(String username) { // MyBatis
		return userDao.findAuthsById(username);
	}

}
