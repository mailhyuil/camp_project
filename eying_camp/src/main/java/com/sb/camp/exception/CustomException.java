package com.sb.camp.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message ) {
        super(message);
        this.errorCode = errorCode;
    }
}
