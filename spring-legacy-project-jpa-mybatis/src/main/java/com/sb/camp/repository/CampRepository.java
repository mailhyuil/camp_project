package com.sb.camp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.Camp;
import com.sb.camp.domain.CampLike;

public interface CampRepository extends JpaRepository<Camp, Long>{
}
