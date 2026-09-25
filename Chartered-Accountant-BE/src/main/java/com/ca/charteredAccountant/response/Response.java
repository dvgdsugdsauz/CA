package com.ca.charteredAccountant.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Envelope for every API response. The Angular client reads {@code statusCode} to decide
 * between data, "nothing to show" (204) and an error message.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

	private int statusCode;
	private String message;
	private Object data;

}
