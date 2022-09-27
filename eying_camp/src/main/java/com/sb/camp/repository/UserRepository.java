package com.sb.camp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findOneByUsername(String username);
}
