package com.sb.camp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.camp.domain.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{
}
