package com.sb.camp.service.impl;

import static com.sb.camp.domain.QCampLike.campLike;
import static com.sb.camp.domain.QUser.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.querydsl.core.QueryModifiers;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sb.camp.domain.Authority;
import com.sb.camp.domain.CampLike;
import com.sb.camp.domain.Pagination;
import com.sb.camp.domain.User;
import com.sb.camp.persistence.UserDao;
import com.sb.camp.repository.CampLikeRepository;
import com.sb.camp.repository.UserRepository;
import com.sb.camp.service.UserService;
import com.sb.camp.util.PaginationUtils;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final CampLikeRepository campLikeRepository;
	private final UserRepository userRepository;
	private final JPAQueryFactory queryFactory;
	
	public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, CampLikeRepository campLikeRepository,
			UserRepository userRepository, JPAQueryFactory queryFactory) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
		this.campLikeRepository = campLikeRepository;
		this.userRepository = userRepository;
		this.queryFactory = queryFactory;
	}

	@Override
	public int join(User user) { // MyBatis
		List<Authority> authList = new ArrayList<>();
		
		if (userDao.selectAll().isEmpty()) {
			Authority auth = new Authority();
			auth.setUser(user);
			auth.setAuthority("ROLE_ADMIN");
			authList.add(auth);
		}
		
		Authority auth = new Authority();
		auth.setAuthority("ROLE_USER");
		auth.setUser(user);
		authList.add(auth);

		if (userDao.findById(user.getUsername()) == null) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userDao.insert(user);
			userDao.insertRole(authList);
		} else {
			return -1;
		}
		return 0;
	}

	@Override
	public User findUser(String username) { // MyBatis
		return userDao.findById(username);
	}

	@Override
	public List<Authority> findAuths(String username) { // MyBatis
		return userDao.findAuthsById(username);
	}

	@Override
	public Map<String,Object> findPaginatedBbsListByUsername(String username, int page) {
		final int totalListCount = userDao.getTotalBbsListSize(username);

		Pagination pagination = PaginationUtils.createPagination(page, totalListCount, 5, 5);

		Map<String, Object> map = new HashMap<>();
		
		map.put("bbs_pagination", pagination);
		map.put("foundBbs", userDao.findBbsByUsername(username, pagination));
		return map;
	}

	@Override
	public Map<String,Object> findPaginatedCampLikeLikeByUsername(String username, int page) {
		User foundUser = userRepository.findOneByUsername(username);
		
		List<CampLike> foundCampLike = campLikeRepository.findByUserId(foundUser.getId());
		
		final int totalListCount = foundCampLike.size();
		
		Pagination pagination = PaginationUtils.createPagination(page, totalListCount, 5, 5);

		QueryModifiers queryModifiers = new QueryModifiers((long) pagination.getLIST_SIZE(),
				(long) pagination.getCri()); // limit, offset
		
		List<CampLike> campLikeList = queryFactory.selectFrom(campLike).where(user.eq(foundUser)).restrict(queryModifiers).fetch();
		
		Map<String, Object> map = new HashMap<>();
		map.put("camplike_pagination", pagination);
		map.put("foundCampLike", campLikeList);
		return map;
	}

}
