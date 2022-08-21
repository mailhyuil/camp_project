package com.sb.camp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.Bbs;

public interface BbsRepository extends JpaRepository<Bbs, Long>{
    public Bbs findOneById(long id);
}
