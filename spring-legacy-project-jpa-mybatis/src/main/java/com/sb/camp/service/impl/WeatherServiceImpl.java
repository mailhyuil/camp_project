package com.sb.camp.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
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
	
	private final Properties api;
	private final StandardPBEStringEncryptor jasypt;
	
	public WeatherServiceImpl(Properties api, StandardPBEStringEncryptor jasypt) {
		this.api = api;
		this.jasypt = jasypt;
	}

	@Override
	public Map<String, Object> getWeatherByLatAndLon(String lat, String lon) {
		String decryptedKey = jasypt.decrypt((String) api.get("weather.serviceKey"));

		URI uri = null;

		try {
			uri = new URI("https://api.openweathermap.org/data/2.5/weather"
					+ "?appid=" + decryptedKey 
					+ "&lat=" + lat 
					+ "&lon=" + lon);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				
		HttpEntity<String> entity = new HttpEntity<String>("parameter", headers);
				
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
