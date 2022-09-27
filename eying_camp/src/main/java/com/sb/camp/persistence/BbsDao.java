package com.sb.camp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Image;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.Video;

public interface BbsDao extends GenericDao<Bbs, Long>{
	public int findBoardListCnt(long campId);
    public List<Bbs> findBoardList(@Param(value = "pagination") Pagination pagination, @Param(value = "campId") long campId);
    
    public int insertImages(List<Image> img);
    public List<Image> findImagesByBbsId(long bbsId);
    
    public int insertVideo(Video video);
    public Video findVideoByBbsId(long bbsId);
    
    public int findImageCount();
    public List<Image> findImages(Pagination pagination);
    
	public void deleteImage(long id);
	public void deleteVideo(Video video);
}
