package com.sb.camp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "camp")
public class Camp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camp_id")
    private long id;
    
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
	private int likeCnt;
	
	@Transient
	private long campId;
	
	@OneToMany(mappedBy = "camp", fetch = FetchType.LAZY)
	private List<Bbs> bbsList = new ArrayList<>();
	
    @OneToMany(mappedBy = "camp",fetch = FetchType.EAGER)
    private List<CampLike> campLikeList = new ArrayList<>();
    
    public void increaseCampLikeCnt() {
        this.likeCnt++;
    }

    public void decreaseCampLikeCnt() {
        this.likeCnt--;
    }
	
	@Override
	public String toString() {
		return "Camp [id=" + id + ", facltNm=" + facltNm + ", lineIntro=" + lineIntro + ", intro=" + intro
				+ ", doNm=" + doNm + ", featureNm=" + featureNm + ", sigunguNm=" + sigunguNm + ", zipcode=" + zipcode
				+ ", addr1=" + addr1 + ", tel=" + tel + ", homepage=" + homepage + ", firstImageUrl=" + firstImageUrl
				+ ", mapX=" + mapX + ", mapY=" + mapY + ", likeCnt=" + likeCnt + "]";
	}
}
