package com.sb.camp.domain.campapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;


@ToString
public class Root {
	@JsonProperty("response")
	public Response response;
}
