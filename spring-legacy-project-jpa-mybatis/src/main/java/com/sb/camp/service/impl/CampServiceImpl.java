package com.sb.camp.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
import com.sb.camp.service.CampService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CampServiceImpl implements CampService{
	
	@Autowired
	private CampDao campDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CampRepository campRepository;
	@Autowired
	private CampLikeRepository campLikeRepository;
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
		
		RestTemplate restTemplate  = new RestTemplate();
				
		ResponseEntity<Root> responseEntity =
		restTemplate.exchange(
				uri, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Root>() {});
		
		List<Camp> camps = responseEntity.getBody().response.body.items.item;
		
		campDao.insertAPI(camps);
	}

	@Override
	public void findByKeywords(Model model, String doNm, String sigunguNm, String facltNm, int page) {
		
//        List<Camp> content = queryFactory
//                .selectFrom(camp)
//                .where(nameContain(name), doNmContain(doNm), sigunguNmContain(sigunguNm))
//                .fetch();
		
		int totalListCount = campDao.getCampListCnt(doNm, sigunguNm, facltNm);
		
		Pagination pagination = new Pagination();
		
		pagination.paginate(page, totalListCount);
		
        model.addAttribute("pagination", pagination);
        model.addAttribute("CAMP_LIST", campDao.getCampList(doNm, sigunguNm, facltNm, pagination.getCri(), pagination.getLIST_SIZE()));
	}

	@Override
	public Camp findCampById(long id) {
		return campDao.findById(id);
	}

	@Override
	@Transactional
	public void likeCamp(long id, String username) {
		Camp foundCamp = campRepository.findById(id).orElseThrow(()->{
			throw new CustomException(ErrorCode.NOT_FOUND_CAMP, "ID에 맞는 캠핑장이 없습니다");
		});
		
		User foundUser = userDao.findById(username);
		
		Optional<CampLike> campLike = campLikeRepository.findByCampAndUser(foundCamp, foundUser);
		
		if (campLike.isPresent()) {
            foundCamp.decreaseCampLikeCnt();
            campLikeRepository.deleteById(campLike.get().getId());
        } else {
        	foundCamp.increaseCampLikeCnt();
        	CampLike saveCampLike = campLikeRepository.save(CampLike.builder()
                    .camp(foundCamp)
                    .user(foundUser)
                    .build());
        	foundCamp.getCampLikeList().add(saveCampLike);
        }
		
		Camp camp = campRepository.save(foundCamp);
		
		System.out.println(camp);
	}
}
