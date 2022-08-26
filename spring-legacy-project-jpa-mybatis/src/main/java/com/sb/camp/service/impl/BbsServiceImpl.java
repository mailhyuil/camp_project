package com.sb.camp.service.impl;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Image;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.Video;
import com.sb.camp.exception.CustomException;
import com.sb.camp.exception.ErrorCode;
import com.sb.camp.persistence.BbsDao;
import com.sb.camp.persistence.UserDao;
import com.sb.camp.service.BbsService;

@Service
@Transactional
public class BbsServiceImpl implements BbsService {

	private final BbsDao bbsDao;
	private final UserDao userDao;
	
	public BbsServiceImpl(BbsDao bbsDao, UserDao userDao) {
		this.bbsDao = bbsDao;
		this.userDao = userDao;
	}

	@Override
	public List<Bbs> selectAll() {
		return bbsDao.selectAll();
	}

	@Override
	public int insertBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files, Principal principal) {
		String loggedInUser = principal.getName();

//		Date date = new Date(System.currentTimeMillis());
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//
//		bbs.setDate(dateFormat.format(date));
//		bbs.setTime(timeFormat.format(date)); // MySQL 함수로 대체
		bbs.setUser(userDao.findById(loggedInUser));


		
		List<MultipartFile> imgList = files.getFiles("files");

		List<Image> imgs = new ArrayList<>();

		for (MultipartFile img : imgList) {
			if(!img.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			String uuidImg = uuid + img.getOriginalFilename();
			Image imgVO = Image.builder().uuidImgName(uuidImg).originalImgName(img.getOriginalFilename()).user(userDao.findById(loggedInUser))
					.bbs(bbs).build();

			File uploadFile = new File("c:/Temp/upload/", uuidImg);

			try {
				img.transferTo(uploadFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			imgs.add(imgVO);
			}
		}
		
		bbsDao.insert(bbs);
		
		if(!imgs.isEmpty()) {
			bbsDao.insertImages(imgs);
		}

		try {
			Video video = Video.builder().bbs(bbs).data(file.getBytes()).user(userDao.findById(loggedInUser)).build();
			bbsDao.insertVideo(video);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Bbs getBbsById(long id) {
		Bbs bbs = bbsDao.findById(id);
		
		if(bbs == null) {
			throw new CustomException(ErrorCode.NOT_FOUND_POST, "게시글을 찾을 수 없습니다.");
		}
		
		bbs.setImgs(bbsDao.findImagesByBbsId(bbs.getBbsId()));
		return bbs;
	}

	@Override
	public Map<String, Object> getPaginationAndBbsList(int page, long campId) {
		int totalListCount = bbsDao.findBoardListCnt(campId);

		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount); // 페이지네이션 초기화
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("bbsList", bbsDao.findBoardList(pagination, campId));
		
		return map;
	}

	@Override
	public void deleteBbs(long id) {
		List<Image> imgs = bbsDao.findImagesByBbsId(id);
		for (Image img : imgs) {

			File file = new File("c:/Temp/upload", img.getUuidImgName());

			if (file.exists()) {
				file.delete();
			}
		}
		bbsDao.delete(id);
	}

	@Override
	public void updateBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files) {
		bbsDao.update(bbs);
		
		List<Image> imgs = bbsDao.findImagesByBbsId(bbs.getBbsId());
		
		for (Image img : imgs) {
			
			File imgFile = new File("c:/Temp/upload", img.getUuidImgName());
			
			if (imgFile.exists()) {
				imgFile.delete();
				bbsDao.deleteImage(img.getImageId());
			}
		}
		
		List<MultipartFile> imgList = files.getFiles("files");

		List<Image> newImgs = new ArrayList<>();

		for (MultipartFile img : imgList) {
			if(!img.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			String uuidImg = uuid + img.getOriginalFilename();
			Image imgVO = Image.builder().uuidImgName(uuidImg).originalImgName(img.getOriginalFilename()).user(bbs.getUser())
					.bbs(bbs).build();

			File uploadFile = new File("c:/Temp/upload/", uuidImg);

			try {
				img.transferTo(uploadFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			newImgs.add(imgVO);
			}
		}
		if(!newImgs.isEmpty()) {
			bbsDao.insertImages(newImgs);
		}

		try {
			if(!file.isEmpty()) {
			Video video = Video.builder().bbs(bbs).data(file.getBytes()).user(bbs.getUser()).build();
			bbsDao.insertVideo(video);
			} else {
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getPaginationAndImageList(int page) {
		int totalListCount = bbsDao.findImageCount();

		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount); // 페이지네이션 초기화
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("imageLIST", bbsDao.findImages(pagination));
		return map;
	}

	@Override
	public Video getVideoByBbsId(long id) {
		return bbsDao.findVideoByBbsId(id);
	}
	
}
