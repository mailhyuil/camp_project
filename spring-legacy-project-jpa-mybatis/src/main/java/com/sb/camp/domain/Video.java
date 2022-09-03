package com.sb.camp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "video")
public class Video extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "video_id")
	private long videoId;

	@Lob
	@Column(nullable = false)
	private byte[] data;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bbs_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Bbs bbs;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	private User user;
	
	@Transient
	private String username;
}
