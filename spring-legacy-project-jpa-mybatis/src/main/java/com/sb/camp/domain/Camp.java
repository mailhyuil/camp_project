package com.sb.camp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "camp")
public class Camp {
    @Id @GeneratedValue
    @Column(name = "camp_id")
    private long id;
	private String facltNm;
	private String lineIntro;
	private String intro;
	private String doNm;
	private String featureNm;
	private String sigunguNm;
	private String zipcode;
	private String addr1;
	private String tel;
	private String homepage;
	private String firstImageUrl;
}
