package com.sb.camp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "bbs")
public class Bbs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bbs_id")
	private long bbsId;

	@Column(columnDefinition = "varchar(50)", nullable = false)
	private String title;

	@Column(columnDefinition = "date", nullable = false)
	private String date;

	@Column(columnDefinition = "time", nullable = false)
	private String time;

	@Column(columnDefinition = "varchar(2048)", nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "camp_id", nullable = false)
	private Camp camp;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bbs")
	private List<Image> imgs = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "bbs")
	private Video video;

	@Transient
	private long campId;

	@Transient
	private String username;
}
