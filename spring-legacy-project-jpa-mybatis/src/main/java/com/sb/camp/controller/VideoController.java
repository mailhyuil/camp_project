package com.sb.camp.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.camp.exception.CustomException;
import com.sb.camp.exception.ErrorCode;
import com.sb.camp.service.BbsService;

@RestController
@RequestMapping("video")
public class VideoController {
	private final BbsService bbsService;
	
    public VideoController(BbsService bbsService) {
		this.bbsService = bbsService;
	}

	@GetMapping("{id}")
    public ResponseEntity<Resource> getVideoByName(@PathVariable("id") long BbsId){
    	if(bbsService.getVideoByBbsId(BbsId).getData() == null) {
    		ResponseEntity.notFound();
    	}
        return ResponseEntity
                .ok(new ByteArrayResource(bbsService.getVideoByBbsId(BbsId).getData()));
    }
}
