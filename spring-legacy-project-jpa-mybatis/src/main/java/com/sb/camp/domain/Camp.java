package com.sb.camp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "camp")
public class Camp {
    @Id
    private String contentId;
	private String facltNm;
	private String lineIntro;
	@Column(columnDefinition = "varchar(1024)")
	private String intro;
	private String doNm;
	private String featureNm;
	private String sigunguNm;
	private String zipcode;
	private String addr1;
	private String tel;
	private String homepage;
	private String firstImageUrl;
	private String mapX;
	private String mapY;
}
