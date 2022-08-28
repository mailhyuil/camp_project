package com.sb.camp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler { // Controller 내에서 발생하는 에러만 해당

    //일반 에러
    @ExceptionHandler
    protected ResponseEntity<Object> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e);
    }

    //요청 바디 검증 실패
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        CustomException e = new CustomException(ErrorCode.BAD_REQUEST_VALIDATION, ex.getMessage());

        return ErrorResponse.toResponseEntity(e);
    }
    
    //모델 검증 실패
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        CustomException e = new CustomException(ErrorCode.BAD_REQUEST_VALIDATION, ex.getMessage());
        return ErrorResponse.toResponseEntity(e);
    }


}