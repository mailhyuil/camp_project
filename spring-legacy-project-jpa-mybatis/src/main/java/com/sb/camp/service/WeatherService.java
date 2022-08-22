package com.sb.camp.service;

import org.springframework.ui.Model;

public interface WeatherService {
	public void getWeatherByLatAndLon(Model model, String lat, String lon);
}
