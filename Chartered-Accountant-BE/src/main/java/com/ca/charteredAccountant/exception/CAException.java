package com.ca.charteredAccountant.exception;

import com.ca.charteredAccountant.common.Constants;

import lombok.Getter;

@Getter
public class CAException extends Exception {

	private static final long serialVersionUID = 1L;

	private final int statusCode;

	public CAException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public static CAException badRequest(String message) {
		return new CAException(Constants.BAD_REQUEST, message);
	}

	public static CAException notFound(String message) {
		return new CAException(Constants.NOT_FOUND, message);
	}

	public static CAException conflict(String message) {
		return new CAException(Constants.CONFLICT, message);
	}

	public static CAException forbidden(String message) {
		return new CAException(Constants.FORBIDDEN, message);
	}

}
