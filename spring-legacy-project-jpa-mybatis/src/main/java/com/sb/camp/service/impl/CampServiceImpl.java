package com.sb.camp.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.transaction.Transactional;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sb.camp.domain.Camp;
import com.sb.camp.domain.CampLike;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.User;
import com.sb.camp.domain.campapi.Root;
import com.sb.camp.exception.CustomException;
import com.sb.camp.exception.ErrorCode;
import com.sb.camp.persistence.CampDao;
import com.sb.camp.repository.CampLikeRepository;
import com.sb.camp.repository.CampRepository;
import com.sb.camp.repository.UserRepository;
import com.sb.camp.service.CampService;

@Service
public class CampServiceImpl implements CampService {

	private final Properties api;
	private final StandardPBEStringEncryptor jasypt;
	private final CampDao campDao;
	private final CampRepository campRepository;
	private final CampLikeRepository campLikeRepository;
	private final UserRepository userRepository;

	public CampServiceImpl(Properties api, StandardPBEStringEncryptor jasypt, CampDao campDao,
			CampRepository campRepository, CampLikeRepository campLikeRepository, UserRepository userRepository) {
		this.api = api;
		this.jasypt = jasypt;
		this.campDao = campDao;
		this.campRepository = campRepository;
		this.campLikeRepository = campLikeRepository;
		this.userRepository = userRepository;
	}

	@Autowired
	@Override
	public void insertAPI() { // api로 json데이터를 받아서 DB에 insert // MyBatis

		String decryptedKey = jasypt.decrypt((String) api.get("camp.serviceKey"));

		URI uri = null;

		try {
			uri = new URI(
					"http://apis.data.go.kr/B551011/GoCamping/basedList"
					+ "?numOfRows=3210"
					+ "&pageNo=1&MobileOS=ETC"
					+ "&MobileApp=AppTest"
					+ "&_type=json&serviceKey="	+ decryptedKey);
		} catch (URISyntaxException e) {
			throw new CustomException(ErrorCode.BAD_URL_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>("parameter", headers);
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Root> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity,
				new ParameterizedTypeReference<Root>() {
				});
		
//		WebClient webClient = WebClient.create("http://apis.data.go.kr/B551011/GoCamping/basedList");
//		
//		Root root = webClient.get()
//				.uri(uri->uri.queryParam("numOfRows", "3210")
//						.queryParam("pageNo", "1")
//						.queryParam("MobileOS", "ETC")
//						.queryParam("MobileApp", "AppTest")
//						.queryParam("_type", "json")
//						.queryParam("serviceKey", decryptedKey).build()
//						)
//				.accept(MediaType.APPLICATION_JSON)
//				.exchange().flatMap(res -> {
//					return res.bodyToMono(Root.class);
//				}).block();
		
		List<Camp> camps = responseEntity.getBody().response.body.items.item;

		campDao.insertAPI(camps);
	}

	@Override
	public Map<String, Object> getPaginationAndCampListByKeywords(String doNm, String sigunguNm, String facltNm,
			int page) { // MyBatis

		final int totalListCount = campDao.findCampListCnt(doNm, sigunguNm, facltNm);
		final int PAGE_SIZE = 5;
		final int LIST_SIZE = 5;

		Pagination pagination = new Pagination();

		pagination.paginate(page, totalListCount, PAGE_SIZE, LIST_SIZE);

		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("campList", campDao.findCampListByKeywords(doNm, sigunguNm, facltNm, pagination.getCri(),
				pagination.getLIST_SIZE()));

		return map;
	}

	@Override
	public Camp getCampById(long id) { // MyBatis
		return campDao.findById(id);
	}

	@Override
	@Transactional
	public void likeCamp(long campId, String username) { // Spring Data JPA

		Camp foundCamp = campRepository.findById(campId).orElseThrow(() -> {
			throw new CustomException(ErrorCode.NOT_FOUND_CAMP, "ID에 맞는 캠핑장이 없습니다");
		});

		User foundUser = userRepository.findOneByUsername(username);

		Optional<CampLike> campLike = campLikeRepository.findByCampIdAndUserId(campId, foundUser.getId());

		if (campLike.isPresent()) {
			foundCamp.decreaseCampLikeCnt();

			campLikeRepository.deleteById(campLike.get().getCampLikeId());
		} else {
			foundCamp.increaseCampLikeCnt();

			CampLike savedCampLike = campLikeRepository
					.save(CampLike.builder().camp(foundCamp).user(foundUser).build());

			foundCamp.getCampLikeList().add(savedCampLike);
		}

	}
}
