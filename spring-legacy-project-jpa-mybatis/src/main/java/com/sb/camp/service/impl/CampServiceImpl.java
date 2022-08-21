package com.sb.camp.service.impl;

import static com.sb.camp.domain.QCamp.camp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sb.camp.domain.Camp;
import com.sb.camp.domain.api.Root;
import com.sb.camp.persistence.CampDao;
import com.sb.camp.service.CampService;

import static org.springframework.util.StringUtils.hasText;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CampServiceImpl implements CampService{
	
	@Autowired
	private CampDao campDao;
	
	@Autowired
	private JPAQueryFactory queryFactory;
	
    private BooleanExpression nameContain(String name) {
        return hasText(name) ? camp.facltNm.contains(name) : null;
    }
    private BooleanExpression sigunguNmContain(String sigunguNm) {
        return hasText(sigunguNm) ? camp.sigunguNm.contains(sigunguNm) : null;
    }
    private BooleanExpression doNmContain(String doNm) {
        return hasText(doNm) ? camp.doNm.contains(doNm) : null;
    }
    
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
	public List<Camp> findByKeywords(String doNm, String sigunguNm, String name) {
		
        List<Camp> content = queryFactory
                .selectFrom(camp)
                .where(nameContain(name), doNmContain(doNm), sigunguNmContain(sigunguNm))
                .fetch();
        
        System.out.println(content);
		return null;
	}

}
