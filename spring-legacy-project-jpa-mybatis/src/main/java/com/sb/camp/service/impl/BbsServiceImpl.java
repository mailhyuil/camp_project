package com.sb.camp.service.impl;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.Image;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.Video;
import com.sb.camp.persistence.BbsDao;
import com.sb.camp.repository.BbsRepository;
import com.sb.camp.repository.ImageRepository;
import com.sb.camp.service.BbsService;

@Service
@Transactional
public class BbsServiceImpl implements BbsService {

	@Autowired
	private BbsDao bbsDao;
	@PersistenceContext
	private EntityManager em;

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
		bbs.setUsername(loggedInUser);

		bbsDao.insert(bbs);

		List<MultipartFile> imgList = files.getFiles("files");

		List<Image> imgs = new ArrayList<>();

		for (MultipartFile img : imgList) {
			String uuid = UUID.randomUUID().toString();
			String uuidImg = uuid + img.getOriginalFilename();
			Image imgVO = Image.builder().img(uuidImg).original_img(img.getOriginalFilename()).username(loggedInUser)
					.bbsId(bbs.getId()).build();

			File uploadFile = new File("c:/Temp/upload/", uuidImg);

			try {
				img.transferTo(uploadFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			imgs.add(imgVO);
		}

		bbsDao.insertImages(imgs);

//      em.persist(img); // 안됨 @Transactional 사용해도 안됨
//      ImageRepository.save(img); // 안됨
//		bbsRepository.save(bbs); // 안됨

		try {
			Video video = Video.builder().bbsId(bbs.getId()).data(file.getBytes()).username(loggedInUser).build();
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
		bbs.setImgs(bbsDao.getImagesByBbsId(bbs.getId()));
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
		
		List<Image> imgs = bbsDao.getImagesByBbsId(bbs.getId());
		
		for (Image img : imgs) {

			File imgFile = new File("c:/Temp/upload", img.getImg());

			if (imgFile.exists()) {
				imgFile.delete();
				bbsDao.deleteImage(img.getId());
			}
		}
		
		List<MultipartFile> imgList = files.getFiles("files");

		List<Image> newImgs = new ArrayList<>();

		for (MultipartFile img : imgList) {
			String uuid = UUID.randomUUID().toString();
			String uuidImg = uuid + img.getOriginalFilename();
			Image imgVO = Image.builder().img(uuidImg).original_img(img.getOriginalFilename()).username(bbs.getUsername())
					.bbsId(bbs.getId()).build();

			File uploadFile = new File("c:/Temp/upload/", uuidImg);

			try {
				img.transferTo(uploadFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			newImgs.add(imgVO);
		}

		bbsDao.insertImages(newImgs);

		try {
			Video video = Video.builder().bbsId(bbs.getId()).data(file.getBytes()).username(bbs.getUsername()).build();
			bbsDao.insertVideo(video);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
