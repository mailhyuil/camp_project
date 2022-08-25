package com.sb.camp.service.impl;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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

	@Autowired
	private BbsDao bbsDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<Bbs> selectAll() {
		return bbsDao.selectAll();
	}

	@Override
	public int insertBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files, Principal principal) {
		String loggedInUser = principal.getName();

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		bbs.setDate(dateFormat.format(date));
		bbs.setTime(timeFormat.format(date));
		bbs.setUser(userDao.findById(loggedInUser));


		
		List<MultipartFile> imgList = files.getFiles("files");

		List<Image> imgs = new ArrayList<>();

		for (MultipartFile img : imgList) {
			if(!img.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			String uuidImg = uuid + img.getOriginalFilename();
			Image imgVO = Image.builder().img(uuidImg).original_img(img.getOriginalFilename()).user(userDao.findById(loggedInUser))
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

//      em.persist(img); // 안됨 @Transactional 사용해도 안됨
//      ImageRepository.save(img); // 안됨
//		bbsRepository.save(bbs); // 안됨

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
	public Bbs findBbsById(Model model, long id) {
		Bbs bbs = bbsDao.findById(id);
		
		if(bbs == null) {
			throw new CustomException(ErrorCode.NOT_FOUND_POST, "게시글을 찾을 수 없습니다.");
		}
		
		bbs.setImgs(bbsDao.getImagesByBbsId(bbs.getBbsId()));
		model.addAttribute("BBS", bbs);
		return bbs;
	}

	@Override
	public void getBbsList(Model model, int page) {
		int totalListCount = bbsDao.getBoardListCnt();

		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount); // 페이지네이션 초기화

		model.addAttribute("pagination", pagination);
		model.addAttribute("BBS_LIST", bbsDao.getBoardList(pagination));
	}

	@Override
	public void deleteBbs(long id) {
		List<Image> imgs = bbsDao.getImagesByBbsId(id);
		for (Image img : imgs) {

			File file = new File("c:/Temp/upload", img.getImg());

			if (file.exists()) {
				file.delete();
			}
		}
		bbsDao.delete(id);
	}

	@Override
	public void updateBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files) {
		bbsDao.update(bbs);
		
		List<Image> imgs = bbsDao.getImagesByBbsId(bbs.getBbsId());
		
		for (Image img : imgs) {
			
			File imgFile = new File("c:/Temp/upload", img.getImg());
			
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
			Image imgVO = Image.builder().img(uuidImg).original_img(img.getOriginalFilename()).user(bbs.getUser())
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
	public List<Image> findImageList(Model model, int page) {
		int totalListCount = bbsDao.getImageCount();

		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount); // 페이지네이션 초기화

		model.addAttribute("pagination", pagination);
		model.addAttribute("IMG_LIST", bbsDao.findImages(pagination));
		return null;
	}

	
}
