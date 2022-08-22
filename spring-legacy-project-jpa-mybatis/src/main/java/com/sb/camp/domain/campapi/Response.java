package com.sb.camp.domain.campapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class Response {
	@JsonProperty("body")
	public Body body;
}
