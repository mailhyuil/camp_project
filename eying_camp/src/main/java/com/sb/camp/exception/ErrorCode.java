package com.sb.camp.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	NOT_FOUND_CAMP(HttpStatus.NOT_FOUND, "해당 캠핑장을 찾을 수 없습니다."),
	NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 포스트를 찾을 수 없습니다."),
	NOT_FOUND_VIDEO(HttpStatus.NOT_FOUND, "해당 비디오를 찾을 수 없습니다."),
	BAD_REQUEST_VALIDATION(HttpStatus.BAD_REQUEST, "검증에 실패하였습니다."),
	BAD_URL_REQUEST(HttpStatus.BAD_REQUEST, "API를 불러오는데 실패하였습니다.");
	
    private final HttpStatus httpStatus;
    private final String detail;
}
