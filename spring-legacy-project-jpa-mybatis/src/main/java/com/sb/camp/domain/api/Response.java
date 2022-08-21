package com.sb.camp.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class Response {
	@JsonProperty("body")
	public Body body;
}
