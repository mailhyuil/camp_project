package com.sb.camp.service.impl;

import static com.sb.camp.domain.QImage.image;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.querydsl.core.QueryModifiers;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
	private final BbsRepository bbsRepository;
	private final BbsLikeRepository bbsLikeRepository;
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final VideoRepository videoRepository;
	private final CampRepository campRepository;
	private final JPAQueryFactory queryFactory;

	public BbsServiceImpl(BbsDao bbsDao, BbsRepository bbsRepository, BbsLikeRepository bbsLikeRepository,
			UserRepository userRepository, ImageRepository imageRepository, VideoRepository videoRepository,
			CampRepository campRepository, JPAQueryFactory queryFactory) {
		this.bbsDao = bbsDao;
		this.bbsRepository = bbsRepository;
		this.bbsLikeRepository = bbsLikeRepository;
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.videoRepository = videoRepository;
		this.campRepository = campRepository;
		this.queryFactory = queryFactory;
	}

	@Override
	public List<Bbs> selectAll() { // MyBatis
		return bbsDao.selectAll();
	}

	@Override
	public int insertBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files, Principal principal) { // Spring Data JPA										
		String loggedInUser = principal.getName();

		User foundUser = userRepository.findOneByUsername(loggedInUser);
		Camp camp = campRepository.findById(bbs.getCampId()).get();
		bbs.setUser(foundUser);
		bbs.setCamp(camp);

		List<MultipartFile> imgList = files.getFiles("files");

		List<Image> imgs = new ArrayList<>();
		
		imgList.stream().filter((item)->{
			return !item.isEmpty();
		}).map((img) -> {
			return createImageFile(img, foundUser, bbs);
		}).forEach((vo) -> imgs.add(vo));
		
		Bbs savedBbs = bbsRepository.save(bbs);

		if (!imgs.isEmpty()) {
			savedBbs.getImgs().addAll(imgs);
			imageRepository.saveAll(imgs);
		}

		if (!file.isEmpty()) {
			try {
				Video video = Video.builder().bbs(bbs).data(file.getBytes()).user(foundUser).build();
				videoRepository.save(video);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public Bbs getBbsById(long id) { // MyBatis
		Bbs bbs = bbsDao.findById(id);

		if (bbs == null) {
			throw new CustomException(ErrorCode.NOT_FOUND_POST, "게시글을 찾을 수 없습니다.");
		}

		bbs.setImgs(bbsDao.findImagesByBbsId(bbs.getId()));
		return bbs;
	}

	@Override
	public Map<String, Object> getPaginationAndBbsList(int page, long campId) { // MyBatis
		final int totalListCount = bbsDao.findBoardListCnt(campId);
		Pagination pagination = createPagination(page, totalListCount);

		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("bbsList", bbsDao.findBoardList(pagination, campId));

		return map;
	}

	@Override
	public void deleteBbs(long id) { // MyBatis
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
	public void updateBbs(Bbs bbs, MultipartFile file, MultipartHttpServletRequest files) { // Spring Data JPA
		Bbs foundBbs = bbsRepository.findById(bbs.getId()).get();
		User foundUser = foundBbs.getUser();

		foundBbs.setTitle(bbs.getTitle());
		foundBbs.setContent(bbs.getContent());

		bbsRepository.save(foundBbs);

		List<Image> imgs = imageRepository.findByBbsId(foundBbs.getId());
		
		imgs.stream().forEach((img)->{
			File imgFile = new File("c:/Temp/upload", img.getUuidImgName());
			if (imgFile.exists()) {
				imgFile.delete();
				imageRepository.deleteById(img.getImageId());
			}
		});

		List<MultipartFile> imgList = files.getFiles("img_files");
		List<Image> newImgs = new ArrayList<>();

		imgList.stream().filter((item)->{
			return !item.isEmpty();
		}).map((img) -> {
			return createImageFile(img, foundUser, foundBbs);
		}).forEach((vo) -> newImgs.add(vo));

		if (!newImgs.isEmpty()) {
			imageRepository.saveAll(newImgs);
		}
		
		if (!file.isEmpty()) {
			try {
				Video video = Video.builder().bbs(foundBbs).data(file.getBytes()).user(foundUser).build();
				videoRepository.save(video);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Map<String, Object> getPaginationAndImageList(int page) { // QueryDSL
		final int totalListCount = (int) imageRepository.count();
		Pagination pagination = createPagination(page, totalListCount);

		QueryModifiers queryModifiers = new QueryModifiers((long) pagination.getLIST_SIZE(), (long) pagination.getCri()); // limit, offset

		List<Image> imageList = queryFactory
				.selectFrom(image)
				.restrict(queryModifiers)
				.fetch();

		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("imageList", imageList);
		return map;
	}

	@Override
	public Video getVideoByBbsId(long id) { // MyBatis
		return bbsDao.findVideoByBbsId(id);
	}

	@Override
	public void likeBbs(long bbsId, String username) { // Spring Data JPA
		Bbs foundBbs = bbsRepository.findById(bbsId).orElseThrow(() -> {
			throw new CustomException(ErrorCode.NOT_FOUND_POST);
		});

		User foundUser = userRepository.findOneByUsername(username);

		Optional<BbsLike> foundBbsLike = bbsLikeRepository.findByBbsIdAndUserId(bbsId, foundUser.getId());

		if (foundBbsLike.isPresent()) {
			foundBbs.decreaseLikeCnt();
			bbsLikeRepository.deleteById(foundBbsLike.get().getBbsLikeId());
		} else {
			foundBbs.increaseLikeCnt();
			BbsLike savedBbsLike = bbsLikeRepository.save(BbsLike.builder()
																	.bbs(foundBbs)
																	.user(foundUser)
																	.build());
			foundBbs.getBbsLikeList().add(savedBbsLike);
		}

	}

	/**
	 * 
	 * @Author sangb
	 * @Date 2022. 8. 31.
	 * @Method createImageFile
	 * @param img
	 * @param User
	 * @param bbs
	 * @return Image
	 */
	private Image createImageFile(MultipartFile img, User User, Bbs bbs) {
		String uuid = UUID.randomUUID().toString();
		String uuidImg = uuid + img.getOriginalFilename();
		Image image = Image.builder()
				.uuidImgName(uuidImg)
				.originalImgName(img.getOriginalFilename())
				.user(User)
				.bbs(bbs)
				.build();

		File uploadFile = new File("c:/Temp/upload/", uuidImg);

		try {
			img.transferTo(uploadFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return image;
	}

	/**
	 * 
	 * @Author sangb
	 * @Date 2022. 8. 31.
	 * @Method createPagination
	 * @param page
	 * @param totalListCount
	 * @return Pagination
	 */
	private Pagination createPagination(int page, int totalListCount) {
		final int PAGE_SIZE = 5;
		final int LIST_SIZE = 5;

		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount, PAGE_SIZE, LIST_SIZE); // 페이지네이션 초기화
		return pagination;
	}
}
