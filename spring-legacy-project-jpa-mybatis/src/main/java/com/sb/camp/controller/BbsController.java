package com.sb.camp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.service.BbsService;

@Controller
@RequestMapping("/bbs")
public class BbsController {

    @Autowired
    private BbsService bbsService;

    @GetMapping("/board")
    public String board(Model model,
                        @RequestParam(required = false, defaultValue = "1") int page){
    	bbsService.getBbsList(model, page);
        return null;
    }

    @PostMapping ("/board")
    public String board(){
        return null;
    }

    @GetMapping("/insert")
    public String insert(@ModelAttribute("BBS") Bbs bbs){
        return "/bbs/insert";
    }

    @PostMapping ("/insert")
    public String insert(Bbs bbs, MultipartHttpServletRequest files, @RequestParam("video") MultipartFile file, Principal principal){
        bbsService.insertBbs(bbs, file, files, principal);
        return "redirect:/bbs/board";
    }

    @GetMapping("/detail")
    public String detail(Model model, long id){
        bbsService.findBbsById(model, id);
        return null;
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") long id){
    	bbsService.deleteBbs(id);
        return "redirect:/bbs/board?id=" + id;
    }
    
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable(name = "id") long id){
    	bbsService.findBbsById(model, id);
        return "/bbs/update";
    }
    
    @PostMapping("/update/{id}")
    public String update(Bbs bbs, MultipartHttpServletRequest files, @RequestParam("video") MultipartFile file){
    	bbsService.updateBbs(bbs, file, files);
        return "redirect:/bbs/board?id=" + bbs.getId();
    }
    
    @GetMapping("/collection")
    public String collection(){
        return null;
    }
}
