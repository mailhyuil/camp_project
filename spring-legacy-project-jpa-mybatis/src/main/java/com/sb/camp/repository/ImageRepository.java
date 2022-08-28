package com.sb.camp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

	public List<Image> findByBbs(Bbs bbs);
}
