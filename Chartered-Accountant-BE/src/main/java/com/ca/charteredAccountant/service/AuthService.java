package com.ca.charteredAccountant.service;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.ChangePasswordRequest;
import com.ca.charteredAccountant.request.LoginRequest;
import com.ca.charteredAccountant.response.AdminUserResponse;
import com.ca.charteredAccountant.response.LoginResponse;

public interface AuthService {

	LoginResponse login(LoginRequest request);

	AdminUserResponse getProfile(Long userId);

	Boolean changePassword(ChangePasswordRequest request, Long userId) throws CAException;

}
