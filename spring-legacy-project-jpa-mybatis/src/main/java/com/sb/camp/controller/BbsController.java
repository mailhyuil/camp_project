package com.sb.camp.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.service.BbsService;

@Controller
@RequestMapping("/bbs")
public class BbsController {

    private final  BbsService bbsService;
    
    public BbsController(BbsService bbsService) {
		this.bbsService = bbsService;
	}

	@GetMapping("/board")
    public String board(Model model, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(name = "id") long campId){
    	Map<String, Object> map = bbsService.getPaginationAndBbsList(page, campId);
    	model.addAllAttributes(map);
    	model.addAttribute("campId", campId);
        return null;
    }

    @PostMapping ("/board")
    public String board(){
        return null;
    }

    @GetMapping("/insert")
    public String insert(Model model, @RequestParam(name = "id") long CampId){
    	Bbs bbs = new Bbs();
    	bbs.setCampId(CampId);
    	model.addAttribute("BBS", bbs);
        return "/bbs/insert";
    }

    @PostMapping ("/insert")
    public String insert(Bbs bbs, MultipartHttpServletRequest files, MultipartFile file, Principal principal){
        bbsService.insertBbs(bbs, file, files, principal);
        return "redirect:/bbs/board?id="+bbs.getCampId();
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam(name = "id") long BbsId){
        model.addAttribute("bbs", bbsService.getBbsById(BbsId));
        return null;
    }
    
    @GetMapping("/delete/{campId}/{id}")
    public String delete(@PathVariable(name = "id") long BbsId,
    		@PathVariable(name="campId") long campId){
    	bbsService.deleteBbs(BbsId);
        return "redirect:/bbs/board?id=" + campId;
    }
    
    @GetMapping("/update/{campId}/{id}")
    public String update(Model model, @PathVariable(name = "id") long id, @PathVariable(name="campId") long campId){
    	model.addAttribute("BBS", bbsService.getBbsById(id));
        return "/bbs/update";
    }
    
    @PostMapping("/update/{campId}/{id}")
    public String update(@PathVariable(name="campId") long campId,
				    		@PathVariable(name = "id") long BbsId,
				    		Bbs bbs, 
				    		MultipartHttpServletRequest img_files,
				    		MultipartFile video_file){
    	bbs.setId(BbsId);
    	bbs.setCampId(campId);
    	
    	bbsService.updateBbs(bbs, video_file, img_files);
        return "redirect:/bbs/board?id=" + campId;
    }
    
    @GetMapping("/collection")
    public String collection(Model model,
            @RequestParam(required = false, defaultValue = "1") int page){
    	model.addAllAttributes(bbsService.getPaginationAndImageList(page));
        return null;
    }
    
    @GetMapping("/goToCamp")
    public String goToCamp(@RequestParam(name = "id") long bbsId) {
    	return "redirect:/bbs/detail?id=" + bbsId;
    }
    
    @GetMapping("/likeBbs/{id}")
    @ResponseBody
    public String likeBbs(@PathVariable(name = "id") long bbsId, Principal principal) {
    	int res = bbsService.likeBbs(bbsId, principal.getName());
    	return res + "";
    }
}
