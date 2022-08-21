package com.sb.camp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A video doesn't exist")
public class VideoNotFoundException extends RuntimeException{
}
