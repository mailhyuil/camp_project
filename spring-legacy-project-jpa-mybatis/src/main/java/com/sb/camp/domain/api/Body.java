package com.sb.camp.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class Body {
	@JsonProperty("items")
	public Items items;
}
