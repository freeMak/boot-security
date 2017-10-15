package com.boot.security.server.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.boot.security.server.dto.ResponseInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * springmvc异常处理
 * 
 * @author 小威老师
 *
 */
@Slf4j(topic = "adminLogger")
@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo badRequestException(IllegalArgumentException exception) {
		return ResponseInfo.builder().code(HttpStatus.BAD_REQUEST.value() + "").message(exception.getMessage()).build();
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
			UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo badRequestException(Exception exception) {
		return ResponseInfo.builder().code(HttpStatus.BAD_REQUEST.value() + "").message(exception.getMessage()).build();
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseInfo exception(Throwable throwable) {
		log.error("系统异常", throwable);
		return ResponseInfo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
				.message(throwable.getMessage()).build();

	}

}
