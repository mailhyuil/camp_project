package com.sb.camp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "bbs")
public class Bbs {
    @Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "varchar(50)")
    private String title;
    private String date;
    private String time;
	@Column(columnDefinition = "varchar(2048)")
    private String content;
    private String username;
    
    @OneToMany(targetEntity = Image.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "bbsId")
    private List<Image> imgs = new ArrayList<>();
}
