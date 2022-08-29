package com.sb.camp.service.impl;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sb.camp.domain.Bbs;
import com.sb.camp.domain.BbsLike;
import com.sb.camp.domain.Camp;
import com.sb.camp.domain.Image;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.User;
import com.sb.camp.domain.Video;
import com.sb.camp.exception.CustomException;
import com.sb.camp.exception.ErrorCode;
import com.sb.camp.persistence.BbsDao;
import com.sb.camp.persistence.UserDao;
import com.sb.camp.repository.BbsLikeRepository;
import com.sb.camp.repository.BbsRepository;
import com.sb.camp.repository.CampRepository;
import com.sb.camp.repository.ImageRepository;
import com.sb.camp.repository.UserRepository;
import com.sb.camp.repository.VideoRepository;
import com.sb.camp.service.BbsService;

@Service
@Transactional
public class BbsServiceImpl implements BbsService {

	private final BbsDao bbsDao;
	private final UserDao userDao;
	private final BbsRepository bbsRepository;
	private final BbsLikeRepository bbsLikeRepository;
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final VideoRepository videoRepository;
	private final CampRepository campRepository;

	public BbsServiceImpl(BbsDao bbsDao, UserDao userDao, BbsRepository bbsRepository,
			BbsLikeRepository bbsLikeRepository, UserRepository userRepository, ImageRepository imageRepository,
			VideoRepository videoRepository, CampRepository campRepository) {
		this.bbsDao = bbsDao;
		this.userDao = userDao;
		this.bbsRepository = bbsRepository;
		this.bbsLikeRepository = bbsLikeRepository;
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.videoRepository = videoRepository;
		this.campRepository = campRepository;
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
		
		User foundUser = userRepository.findOneByUsername(loggedInUser);
		Camp camp = campRepository.findById(bbs.getCampId()).get();
		bbs.setUser(foundUser);
		bbs.setCamp(camp);
		
		// @CreatedBy @LastModifiedBy 안됨
		bbs.setCreatedBy(loggedInUser);
		bbs.setLastModifiedBy(loggedInUser);
		
		List<MultipartFile> imgList = files.getFiles("files");

		List<Image> imgs = new ArrayList<>();

		for (MultipartFile img : imgList) {
			if(!img.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			String uuidImg = uuid + img.getOriginalFilename();
			Image imgVO = Image.builder()
					.uuidImgName(uuidImg)
					.originalImgName(img.getOriginalFilename())
					.user(foundUser)
					.bbs(bbs)
					.build();

			File uploadFile = new File("c:/Temp/upload/", uuidImg);

			try {
				img.transferTo(uploadFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			imgs.add(imgVO);
			}
		}
		
		Bbs savedBbs = bbsRepository.save(bbs);
		
		if(!imgs.isEmpty()) {
			savedBbs.getImgs().addAll(imgs);
			imageRepository.saveAll(imgs);
		}
		
		if(!file.isEmpty()) {
			try {
				Video video = Video.builder()
						.bbs(bbs)
						.data(file.getBytes())
						.user(foundUser)
						.build();
				videoRepository.save(video);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return 0;
	}

	@Override
	public Bbs getBbsById(long id) {
		Bbs bbs = bbsDao.findById(id);
		
		if(bbs == null) {
			throw new CustomException(ErrorCode.NOT_FOUND_POST, "게시글을 찾을 수 없습니다.");
		}
		
		bbs.setImgs(bbsDao.findImagesByBbsId(bbs.getId()));
		return bbs;
	}

	@Override
	public Map<String, Object> getPaginationAndBbsList(int page, long campId) {
		final int totalListCount = bbsDao.findBoardListCnt(campId);
		final int PAGE_SIZE = 5;
		final int LIST_SIZE = 5;
		
		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount, PAGE_SIZE, LIST_SIZE); // 페이지네이션 초기화
		
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
		bbsRepository.deleteById(id);
	}

	@Override
	public void updateBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files) {
		Bbs foundBbs = bbsRepository.findById(bbs.getId()).get();
		User foundUser = foundBbs.getUser();
		foundBbs.setTitle(bbs.getTitle());
		foundBbs.setContent(bbs.getContent());

		bbsRepository.save(foundBbs);
		
		List<Image> imgs = imageRepository.findByBbs(foundBbs);
		
		for (Image img : imgs) {
			
			File imgFile = new File("c:/Temp/upload", img.getUuidImgName());
			
			if (imgFile.exists()) {
				imgFile.delete();
				imageRepository.deleteById(img.getImageId());}
		}
		
		List<MultipartFile> imgList = files.getFiles("img_files");

		List<Image> newImgs = new ArrayList<>();

		for (MultipartFile img : imgList) {
			if(!img.isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			String uuidImg = uuid + img.getOriginalFilename();
			Image imgVO = Image.builder().uuidImgName(uuidImg).originalImgName(img.getOriginalFilename()).user(foundUser)
					.bbs(foundBbs).build();

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
			imageRepository.saveAll(newImgs);
		}

		if(!file.isEmpty()) {
			try {
				Video video = Video.builder()
						.bbs(foundBbs)
						.data(file.getBytes())
						.user(foundUser)
						.build();
				videoRepository.save(video);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			videoRepository.deleteById(foundBbs.getVideo().getVideoId());
		}
	}

	@Override
	public Map<String, Object> getPaginationAndImageList(int page) {
		final int totalListCount = bbsDao.findImageCount();
		final int PAGE_SIZE = 5;
		final int LIST_SIZE = 5;
		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount, PAGE_SIZE, LIST_SIZE); // 페이지네이션 초기화
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("imageLIST", bbsDao.findImages(pagination));
		return map;
	}

	@Override
	public Video getVideoByBbsId(long id) {
		return bbsDao.findVideoByBbsId(id);
	}

	@Override
	public void likeBbs(long bbsId, String username) {
		Bbs foundBbs = bbsRepository.findById(bbsId).orElseThrow(()->{
			throw new CustomException(ErrorCode.NOT_FOUND_POST);
		});
		
		User foundUser = userRepository.findOneByUsername(username);
		
		Optional<BbsLike> foundBbsLike = bbsLikeRepository.findByBbsIdAndUserId(bbsId, foundUser.getId());
		
		if(foundBbsLike.isPresent()) {
			foundBbs.decreaseLikeCnt();
			bbsLikeRepository.deleteById(foundBbsLike.get().getBbsLikeId());
		} else {
			foundBbs.increaseLikeCnt();
			BbsLike savedBbsLike = bbsLikeRepository.save(BbsLike.builder().bbs(foundBbs).user(foundUser).build());
			foundBbs.getBbsLikeList().add(savedBbsLike);
		}
		
	}
	
}
