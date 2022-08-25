package com.sb.camp.persistence;

import org.apache.ibatis.annotations.Param;

import com.sb.camp.domain.CampLike;

public interface CampLikeDao extends GenericDao<CampLike, Long> {

	public void deleteById(long camp_like_id);

	public void save(CampLike campLike);

	public CampLike findByCampIdAndUsername(@Param(value = "camp_id") long camp_id, @Param(value = "username") String username);
}
