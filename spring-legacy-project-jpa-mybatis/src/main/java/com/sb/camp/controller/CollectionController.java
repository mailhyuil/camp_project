package com.sb.camp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.camp.domain.Image;
import com.sb.camp.persistence.BbsDao;

@RestController
public class CollectionController {
	@Autowired
	private BbsDao bbsDao;
	
    @PostMapping("/bbs/collection/{limit}/{offset}")
    public ResponseEntity<List<Image>> collection(@PathVariable("limit") int limit,
    		 @PathVariable("offset") int offset){
    	System.out.println(limit);
    	System.out.println(offset);
        return ResponseEntity
                .ok(bbsDao.findImages(limit, offset));
    }
}
