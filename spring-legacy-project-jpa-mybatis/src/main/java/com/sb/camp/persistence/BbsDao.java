package com.sb.camp.persistence;

import java.util.List;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Image;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.Video;

public interface BbsDao extends GenericDao<Bbs, Long>{
    public List<Bbs> getBoardList(Pagination pagination);
    public int getBoardListCnt();
    public int insertImages(List<Image> img);
    public List<Image> getImagesByBbsId(long bbsId);
    public int insertVideo(Video video);
    public Video getVideoByBbsId(long bbsId);
}
