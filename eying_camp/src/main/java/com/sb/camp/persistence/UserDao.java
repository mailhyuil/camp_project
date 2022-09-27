package com.sb.camp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sb.camp.domain.Authority;
import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.User;

public interface UserDao extends GenericDao<User, String> {
	public List<Authority> findAuthsById(String username);
	public int insertRole(List<Authority> auths);
	public List<Bbs> findBbsByUsername(@Param(value = "username") String username, @Param(value = "pagination") Pagination pagination);
	public int getTotalBbsListSize(String username);
}
