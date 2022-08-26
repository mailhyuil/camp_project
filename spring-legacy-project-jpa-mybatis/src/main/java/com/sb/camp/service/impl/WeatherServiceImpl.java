package com.sb.camp.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sb.camp.domain.weatherapi.WeatherRoot;
import com.sb.camp.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService{

	@Override
	public Map<String, Object> getWeatherByLatAndLon(String lat, String lon) {
		URI uri = null;
		try {
			uri = new URI("https://api.openweathermap.org/data/2.5/weather?appid=945a820a0cfd85e6354d9c2a9a628ba9&lat="+lat+"&lon="+lon);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				
		HttpEntity<String> entity = new HttpEntity<String>("parameter", headers);
		// 헤더에 넣은 데이터를 엔티티로 변환 * 헤더에 넣을 데이터가 없다면 null로 설정
				
		RestTemplate restTemplate  = new RestTemplate();
				
		ResponseEntity<WeatherRoot> respEntity =
		restTemplate.exchange(
				uri, 
				HttpMethod.GET,
				entity,
				new ParameterizedTypeReference<WeatherRoot>() {});
				
		Map<String, Object> map = new HashMap<>();
		map.put("WEATHER", respEntity.getBody().getWeather().get(0));
		map.put("MAIN", respEntity.getBody().getMain());
		map.put("WIND", respEntity.getBody().getWind());
		
		return map;
	}

}
