package com.sb.camp.domain;

import java.util.ArrayList;
import java.util.List;

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
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String date;
    private String time;
    private String content;
    private String username;
    
    @OneToMany(targetEntity = Image.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bbsId")
    private List<Image> imgs = new ArrayList<>();
}
