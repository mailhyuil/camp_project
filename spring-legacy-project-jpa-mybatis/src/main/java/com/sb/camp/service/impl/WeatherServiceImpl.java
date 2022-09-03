package com.sb.camp.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

		WebClient webClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather");

		WeatherRoot weather = webClient.get()
				.uri("https://api.openweathermap.org/data/2.5/weather"
						+ "?appid=" + decryptedKey 
						+ "&lat=" + lat 
						+ "&lon=" + lon)
				.accept(MediaType.APPLICATION_JSON).exchange().flatMap(res -> {
					return res.bodyToMono(WeatherRoot.class);
				}).block();
		
		Map<String, Object> map = new HashMap<>();
		map.put("WEATHER", weather.getWeather().get(0));
		map.put("MAIN", weather.getMain());
		map.put("WIND", weather.getWind());
		
		return map;
	}

}
