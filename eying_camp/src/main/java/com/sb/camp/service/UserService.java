package com.sb.camp.service;

import java.util.List;
import java.util.Map;

import com.sb.camp.domain.Authority;
import com.sb.camp.domain.User;

public interface UserService {
	public int join(User user);
	public User findUser(String username);
	public List<Authority> findAuths(String username);
	public Map<String, Object> findPaginatedBbsListByUsername(String username, int page);
	public Map<String, Object> findPaginatedCampLikeLikeByUsername(String username, int page);
}
