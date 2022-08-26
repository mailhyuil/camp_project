package com.sb.camp.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

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
import com.sb.camp.persistence.UserDao;
import com.sb.camp.repository.CampLikeRepository;
import com.sb.camp.repository.CampRepository;
import com.sb.camp.repository.UserRepository;
import com.sb.camp.service.CampService;

@Service
public class CampServiceImpl implements CampService{
	
	@Autowired
	private CampDao campDao;
	@Autowired
	private CampRepository campRepository;
	@Autowired
	private CampLikeRepository campLikeRepository;
	@Autowired
	private UserRepository userRepository;
	
//    private BooleanExpression nameContain(String name) {
//        return hasText(name) ? camp.facltNm.contains(name) : null;
//    }
//    private BooleanExpression sigunguNmContain(String sigunguNm) {
//        return hasText(sigunguNm) ? camp.sigunguNm.contains(sigunguNm) : null;
//    }
//    private BooleanExpression doNmContain(String doNm) {
//        return hasText(doNm) ? camp.doNm.contains(doNm) : null;
//    }
    
	@Autowired
	@Override
	public void insertAPI() {
		// api로 json데이터를 받아서 DB에 insert
		// DB에 json데이터가 들어있을시에는 업데이트
				
		URI uri = null;
		try {
			uri = new URI("http://apis.data.go.kr/B551011/GoCamping/basedList?numOfRows=3210&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&serviceKey=ffkOTTIQjptMoutfl2u8mBbET9rz9SwV0tjydrgfRI65Ucp2DQJ52JGOLf8y5wfMsd%2F0OljkN1bWHikvsiZzBg%3D%3D");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				
		HttpEntity<String> entity = new HttpEntity<String>("parameter", headers);
		RestTemplate restTemplate  = new RestTemplate();
				
		ResponseEntity<Root> responseEntity =
		restTemplate.exchange(
				uri, 
				HttpMethod.GET,
				entity,
				new ParameterizedTypeReference<Root>() {});
		
		List<Camp> camps = responseEntity.getBody().response.body.items.item;
		
		campDao.insertAPI(camps);
	}

	@Override
	public Map<String, Object> getPaginationAndCampListByKeywords(String doNm, String sigunguNm, String facltNm, int page) {
		
//        List<Camp> content = queryFactory
//                .selectFrom(camp)
//                .where(nameContain(name), doNmContain(doNm), sigunguNmContain(sigunguNm))
//                .fetch();
		
		int totalListCount = campDao.findCampListCnt(doNm, sigunguNm, facltNm);
		
		Pagination pagination = new Pagination();
		
		pagination.paginate(page, totalListCount);
		
		Map<String, Object> map = new HashMap<>();
        map.put("pagination", pagination);
        map.put("campList", campDao.findCampListByKeywords(doNm, sigunguNm, facltNm, pagination.getCri(), pagination.getLIST_SIZE()));
        
        return map;
	}

	@Override
	public Camp getCampById(long id) {
		return campDao.findById(id);
	}

	@Override
	@Transactional
	public void likeCamp(long campId, String username) { // Spring Data JPA 구현
		
		Camp foundCamp = campRepository.findById(campId).orElseThrow(()->{
			throw new CustomException(ErrorCode.NOT_FOUND_CAMP, "ID에 맞는 캠핑장이 없습니다");
		});
		
		User foundUser = userRepository.findOneByUsername(username);
		
		Optional<CampLike> campLike = campLikeRepository.findByCampIdAndUserId(campId, foundUser.getId());
		System.out.println("campLike " + campLike);
		
		if (campLike.isPresent()) {
			foundCamp.decreaseCampLikeCnt();
			
			campLikeRepository.deleteById(campLike.get().getCampLikeId());
        } else {
        	foundCamp.increaseCampLikeCnt();
        	
        	CampLike savedCampLike = campLikeRepository.save(CampLike.builder()
        			.camp(foundCamp)
        			.user(foundUser)
        			.build());
        	
        	foundCamp.getCampLikeList().add(savedCampLike);
        }
		
	}
}
