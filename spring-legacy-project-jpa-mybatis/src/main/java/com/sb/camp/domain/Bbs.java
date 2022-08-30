package com.sb.camp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.camp.domain.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "bbs")
@EntityListeners(AuditingEntityListener.class)
public class Bbs extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bbs_id")
	private long id;

	@Column(columnDefinition = "varchar(50)", nullable = false)
	private String title;
	
	@Column(columnDefinition = "varchar(2048)", nullable = false)
	private String content;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "camp_id", nullable = false)
	private Camp camp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bbs", cascade = CascadeType.REMOVE)
	private List<BbsLike> bbsLikeList = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bbs")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Image> imgs = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "bbs", cascade = CascadeType.REMOVE)
	private Video video;
	
	@Column(columnDefinition = "INT DEFAULT 0")
	private int likeCnt;
    
	public void increaseLikeCnt() {
		this.likeCnt++;
	}
	
	public void decreaseLikeCnt() {
		this.likeCnt--;
	}
	
	@Transient
	private long campId;

	@Transient
	private String username;

}
