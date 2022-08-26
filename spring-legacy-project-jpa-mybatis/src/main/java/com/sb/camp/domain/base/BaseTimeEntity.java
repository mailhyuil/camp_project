package com.sb.camp.domain.base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @Column(updatable = false, length = 19)
    @CreatedDate
    private String createdDate;

    @LastModifiedDate
    @Column(length = 19)
    private String lastModifiedDate;
    
    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.lastModifiedDate = this.createdDate;
    }
    
    @PreUpdate
    public void onPreUpdate(){
    this.lastModifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
