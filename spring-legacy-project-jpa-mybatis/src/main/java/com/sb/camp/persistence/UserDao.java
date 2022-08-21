package com.sb.camp.persistence;

import java.util.List;

import com.sb.camp.domain.Authority;
import com.sb.camp.domain.User;

public interface UserDao extends GenericDao<User, String> {
	public List<Authority> findAuthsById(String username);
	public int insertRole(List<Authority> auths);
}
