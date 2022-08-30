package com.sb.camp.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sb.camp.domain.Bbs;
import com.sb.camp.repository.BbsRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/*-context.xml"})
public class BbsServiceImplTest {
	@Autowired
	private BbsRepository bbsRepository;
	
	@Test
	public void test() {
		Optional<Bbs> bbs = bbsRepository.findById(5l);
		assertThat(bbs.get(), is(notNullValue()));
	}

}
