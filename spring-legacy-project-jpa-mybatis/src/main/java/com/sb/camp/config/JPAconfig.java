package com.sb.camp.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class JPAConfig {
	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
	    return new JPAQueryFactory(entityManager);
	}
	
	@Bean
	public AuditorAware<String> auditorAware(){
		return new AwareAuditConfig();
	}
}
