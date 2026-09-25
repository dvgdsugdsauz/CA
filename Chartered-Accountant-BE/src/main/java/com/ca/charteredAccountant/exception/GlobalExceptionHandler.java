package com.ca.charteredAccountant.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CAException.class)
	public ResponseEntity<Response> handleCAException(CAException ex) {
		return build(ex.getStatusCode(), ex.getMessage(), null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new LinkedHashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.putIfAbsent(error.getField(), error.getDefaultMessage());
		}
		return build(Constants.BAD_REQUEST, Constants.ResponseMessages.VALIDATION_FAILED_MESSAGE, errors);
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
			MissingServletRequestParameterException.class, MissingServletRequestPartException.class })
	public ResponseEntity<Response> handleBadInput(Exception ex) {
		return build(Constants.BAD_REQUEST, Constants.ResponseMessages.INPUT_REQUIRED_MESSAGE, null);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Response> handleBadCredentials(BadCredentialsException ex) {
		return build(Constants.UNAUTHORIZED, Constants.ResponseMessages.INVALID_CREDENTIALS_MESSAGE, null);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<Response> handleDisabled(DisabledException ex) {
		return build(Constants.UNAUTHORIZED, Constants.ResponseMessages.ACCOUNT_DISABLED_MESSAGE, null);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Response> handleAccessDenied(AccessDeniedException ex) {
		return build(Constants.FORBIDDEN, Constants.ResponseMessages.FORBIDDEN_MESSAGE, null);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Response> handleDataIntegrity(DataIntegrityViolationException ex) {
		log.warn("Data integrity violation: {}", ex.getMostSpecificCause().getMessage());
		return build(Constants.CONFLICT, Constants.ResponseMessages.DUPLICATE_MESSAGE, null);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Response> handleUploadSize(MaxUploadSizeExceededException ex) {
		return build(Constants.PAYLOAD_TOO_LARGE, Constants.ResponseMessages.FILE_TOO_LARGE_MESSAGE, null);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Response> handleNoResource(NoResourceFoundException ex) {
		return build(Constants.NOT_FOUND, "The requested resource was not found", null);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Response> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		return build(Constants.METHOD_NOT_ALLOWED, "Request method " + ex.getMethod() + " is not supported here", null);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleUnexpected(Exception ex) {
		log.error("Unhandled exception", ex);
		return build(Constants.INTERNAL_SERVER_ERROR, Constants.ResponseMessages.INTERNAL_ERROR_MESSAGE, null);
	}

	private ResponseEntity<Response> build(int statusCode, String message, Object data) {
		HttpStatus status = HttpStatus.resolve(statusCode);
		return ResponseEntity.status(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Response(statusCode, message, data));
	}

}
