package com.sb.camp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
	private long imageId;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bbs_id")
	private Bbs bbs;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;
	
	private String img;
	private String original_img;
	
}
