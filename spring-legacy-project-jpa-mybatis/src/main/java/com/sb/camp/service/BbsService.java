package com.sb.camp.service;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Image;

public interface BbsService {
    public List<Bbs> selectAll();

    public int insertBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files, Principal principal);

    public Bbs findBbsById(Model model, long id);

	public void getBbsList(Model model, int page);

	public void deleteBbs(long id);

	public void updateBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files);

	public List<Image> findImageList(Model model, int page);
}
