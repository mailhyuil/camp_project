package com.sb.camp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.Camp;
import com.sb.camp.domain.CampLike;
import com.sb.camp.domain.User;

public interface CampLikeRepository extends JpaRepository<CampLike, Long>{
	
	Optional<CampLike> findByCampAndUser(Camp camp, User user);
}
