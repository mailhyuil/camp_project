package com.sb.camp.domain.weatherapi;

import java.util.List;

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
}
