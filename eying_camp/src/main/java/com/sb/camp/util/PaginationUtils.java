package com.sb.camp.util;

import com.sb.camp.domain.Pagination;

public class PaginationUtils {
	/**
	 * 
	 * @Author sangb
	 * @Date 2022. 9. 5.
	 * @Method createPagination
	 * @param page
	 * @param totalListCount
	 * @param PAGE_SIZE
	 * @param LIST_SIZE
	 * @return Pagination
	 */
	public static Pagination createPagination(int page, int totalListCount, int PAGE_SIZE, int LIST_SIZE) {

		Pagination pagination = new Pagination(); // 페이지네이션 객체 생성

		pagination.paginate(page, totalListCount, PAGE_SIZE, LIST_SIZE); // 페이지네이션 초기화
		return pagination;
	}
}
