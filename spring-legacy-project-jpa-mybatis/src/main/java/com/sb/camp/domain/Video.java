package com.sb.camp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
public class Video{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private long bbsId;

    @Lob
    private byte[] data;

    private String username;
    
}
