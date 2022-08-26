package com.sb.camp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.camp.domain.CampLike;

@Repository
public interface CampLikeRepository extends JpaRepository<CampLike, Long>{
	Optional<CampLike> findByCampIdAndUserId(long campId, long userId);
}
