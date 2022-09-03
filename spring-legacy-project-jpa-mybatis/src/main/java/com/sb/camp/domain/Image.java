package com.sb.camp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.camp.domain.base.BaseEntity;

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
@ToString

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "image")
public class Image extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private long imageId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bbs_id", nullable = false)
	private Bbs bbs;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private String uuidImgName;
	
	@Column(nullable = false)
	private String originalImgName;
	
	@Transient
	private String username;
}
