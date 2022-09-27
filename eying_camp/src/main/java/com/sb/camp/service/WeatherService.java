package com.sb.camp.service;

import java.util.Map;

public interface WeatherService {
	public Map<String, Object> getWeatherByLatAndLon(String lat, String lon);
}
