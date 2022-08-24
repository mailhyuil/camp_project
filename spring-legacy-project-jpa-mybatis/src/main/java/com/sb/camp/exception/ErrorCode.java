package com.sb.camp.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	NOT_FOUND_CAMP(HttpStatus.NOT_FOUND, "해당 캠핑장을 찾을 수 없습니다."),
	NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 포스트를 찾을 수 없습니다."),
	NOT_FOUND_VIDEO(HttpStatus.NOT_FOUND, "해당 비디오를 찾을 수 없습니다.");
	
    private final HttpStatus httpStatus;
    private final String detail;
	
}
