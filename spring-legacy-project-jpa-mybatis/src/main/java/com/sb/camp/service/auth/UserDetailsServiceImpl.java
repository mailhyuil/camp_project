package com.sb.camp.service.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sb.camp.domain.Authority;
import com.sb.camp.domain.User;
import com.sb.camp.persistence.UserDao;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserDao userDao;

	public UserDetailsServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * 로그인 한 사용자의 username 을 매개변수로 전달받아
	 * UserDao 를 통해 로그인한 사용자 정보를 DB 로 부터 가져온다
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findById(username);

		if(user == null) {
			throw new UsernameNotFoundException(username + " : 회원가입을 해주세요");
		}

		List<Authority> authList = userDao.findAuthsById(username);

		List<GrantedAuthority> grantList = new ArrayList<>();
		
		for(Authority auth : authList) {
			grantList.add(new SimpleGrantedAuthority(auth.getAuthority()));
		}

		user.setAuthorities(grantList);

		return user;
	}

}
