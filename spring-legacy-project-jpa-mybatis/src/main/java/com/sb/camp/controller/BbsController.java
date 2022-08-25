package com.sb.camp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Camp;
import com.sb.camp.domain.Image;
import com.sb.camp.service.BbsService;

@Controller
@RequestMapping("/bbs")
public class BbsController {

    @Autowired
    private BbsService bbsService;

    @GetMapping("/board")
    public String board(Model model, @RequestParam(required = false, defaultValue = "1") int page, long id){
    	bbsService.getBbsList(model, page, id);
    	model.addAttribute("campId", id);
        return null;
    }

    @PostMapping ("/board")
    public String board(){
        return null;
    }

    @GetMapping("/insert")
    public String insert(Model model, long id){
    	Bbs bbs = new Bbs();
    	bbs.setCampId(id);
    	model.addAttribute("BBS", bbs);
        return "/bbs/insert";
    }

    @PostMapping ("/insert")
    public String insert(Bbs bbs, MultipartHttpServletRequest files, @RequestParam("video") MultipartFile file, Principal principal){
    	System.out.println(bbs);
        bbsService.insertBbs(bbs, file, files, principal);
        return "redirect:/bbs/board?id="+bbs.getCampId();
    }

    @GetMapping("/detail")
    public String detail(Model model, long id){
        bbsService.findBbsById(model, id);
        return null;
    }
    
    @GetMapping("/delete/{campId}/{id}")
    public String delete(@PathVariable(name = "id") long id,
    		@PathVariable(name="campId") long campId){
    	bbsService.deleteBbs(id);
        return "redirect:/bbs/board?id=" + campId;
    }
    
    @GetMapping("/update/{campId}/{id}")
    public String update(Model model, @PathVariable(name = "id") long id,@PathVariable(name="campId") long campId){
    	bbsService.findBbsById(model, id);
        return "/bbs/update";
    }
    
    @PostMapping("/update/{campId}/{id}")
    public String update(@PathVariable(name="campId") long campId, @PathVariable(name = "id") long id, Bbs bbs, MultipartHttpServletRequest files, @RequestParam("video") MultipartFile file){
    	bbs.setBbsId(id);
    	bbsService.updateBbs(bbs, file, files);
        return "redirect:/bbs/board?id=" + campId;
    }
    
    @GetMapping("/collection")
    public String collection(Model model,
            @RequestParam(required = false, defaultValue = "1") int page){
    	List<Image> imgList = bbsService.findImageList(model, page);
        return null;
    }
    @GetMapping("/goToCamp")
    public String goToCamp(Model model, long id) {
    	Bbs bbs = bbsService.findBbsById(model, id);
    	System.out.println(bbs.getCampId());
    	return "redirect:/camp/detail?id=" + bbs.getCampId();
    }
}
