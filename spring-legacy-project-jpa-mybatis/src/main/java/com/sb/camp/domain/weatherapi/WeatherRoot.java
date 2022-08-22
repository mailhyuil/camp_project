package com.sb.camp.domain.weatherapi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class WeatherRoot {
	@JsonProperty("weather")
	public List<Weather> weather;
	@JsonProperty("main")
	public Main main;
	@JsonProperty("wind")
	public Wind wind;
	
	public static void main(String[] args) {
		URI uri = null;
		try {
			uri = new URI("https://api.openweathermap.org/data/2.5/weather?lat=37.7278127&lon=127.5112565&appid=945a820a0cfd85e6354d9c2a9a628ba9");
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
				
		System.out.println(respEntity.getBody().getWeather());
	}
}
