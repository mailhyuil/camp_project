package com.sb.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.camp.service.BbsService;

@RestController
@RequestMapping("video")
public class VideoController {
	@Autowired
	private BbsService bbsService;
	
    @GetMapping("{id}")
    public ResponseEntity<Resource> getVideoByName(@PathVariable("id") long BbsId){
    	
        return ResponseEntity
                .ok(new ByteArrayResource(bbsService.getVideoByBbsId(BbsId).getData()));
    }
}
