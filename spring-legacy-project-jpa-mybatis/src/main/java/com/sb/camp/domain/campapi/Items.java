package com.sb.camp.domain.campapi;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sb.camp.domain.Camp;

import lombok.ToString;


@ToString
public class Items {
	@JsonProperty("item")
	public List<Camp> item;
}
