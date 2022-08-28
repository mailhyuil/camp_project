package com.sb.camp.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Video;

public interface BbsService {
    public List<Bbs> selectAll();

    public int insertBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files, Principal principal);

    public Bbs getBbsById(long id);

	public Map<String, Object> getPaginationAndBbsList(int page, long campId);

	public void deleteBbs(long id);

	public void updateBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files);

	public Map<String, Object> getPaginationAndImageList(int page);

	public Video getVideoByBbsId(long id);

	public void likeBbs(long bbsId, String username);
}
