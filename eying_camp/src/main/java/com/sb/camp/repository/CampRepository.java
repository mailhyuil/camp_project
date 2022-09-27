package com.sb.camp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.Camp;

public interface CampRepository extends JpaRepository<Camp, Long>{
}
