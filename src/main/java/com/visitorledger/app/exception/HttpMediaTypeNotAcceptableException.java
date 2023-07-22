package com.visitorledger.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class HttpMediaTypeNotAcceptableException extends RuntimeException {

	public HttpMediaTypeNotAcceptableException() {
		super();
	}

	public HttpMediaTypeNotAcceptableException(String message) {
		super(message);
	}

	public HttpMediaTypeNotAcceptableException(String message, Throwable cause) {
		super(message, cause);
	}
}
