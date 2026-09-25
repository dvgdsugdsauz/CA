package com.ca.charteredAccountant.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.response.Response;

public abstract class BaseController {

	protected LoggedInUserDetails getLoggedInUserDetails() throws CAException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof LoggedInUserDetails user) {
			return user;
		}
		throw new CAException(Constants.UNAUTHORIZED, Constants.ResponseMessages.UNAUTHORIZED_MESSAGE);
	}

	protected ResponseEntity<Response> getOKResponseEntity(Response response) {
		return ResponseEntity.ok(response);
	}

	protected Pageable buildPageable(Integer page, Integer size, Sort sort) {
		int pageNo = page == null || page < 0 ? Constants.DEFAULT_PAGE : page;
		int pageSize = size == null || size < 1 ? Constants.DEFAULT_PAGE_SIZE : Math.min(size, Constants.MAX_PAGE_SIZE);
		return PageRequest.of(pageNo, pageSize, sort);
	}

}
