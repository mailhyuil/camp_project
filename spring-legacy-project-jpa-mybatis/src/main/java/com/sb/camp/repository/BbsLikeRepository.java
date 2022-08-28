package com.sb.camp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.BbsLike;

public interface BbsLikeRepository extends JpaRepository<BbsLike, Long>{
	public Optional<BbsLike> findByBbsIdAndUserId(long bbsId, long userId);
}
