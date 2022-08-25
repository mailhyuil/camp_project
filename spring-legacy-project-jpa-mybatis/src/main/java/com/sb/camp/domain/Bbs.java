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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    
    @Column(columnDefinition = "varchar(50)")
    private String title;
    private String date;
    private String time;
	@Column(columnDefinition = "varchar(2048)")
    private String content;
	@Column(name = "camp_id")
	private long campId;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "username")
    private User user;
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bbs")
    private List<Image> imgs = new ArrayList<>();
    
    @Transient
    private String username;
}
