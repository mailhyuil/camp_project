package com.sb.camp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "bbsLike")
public class BbsLike{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bbs_like_id")
	private long bbsLikeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bbs_id", nullable = false)
	private Bbs bbs;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
