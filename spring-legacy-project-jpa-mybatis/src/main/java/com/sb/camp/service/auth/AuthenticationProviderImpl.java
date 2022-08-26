package com.sb.camp.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sb.camp.domain.User;

@Service("authenticationProvider")
public class AuthenticationProviderImpl implements AuthenticationProvider{
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		User user = (User) userService.loadUserByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username+"은 가입되지 않은 회원입니다.");
		}
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("비밀번호가 틀립니다.");
		}
		
		return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
