package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.AdminUserRequest;
import com.ca.charteredAccountant.response.AdminUserResponse;
import com.ca.charteredAccountant.response.DropdownResponse;

public interface AdminUserService {

	List<AdminUserResponse> getAllUsers();

	List<DropdownResponse> getUserDropdown();

	Boolean saveUser(AdminUserRequest request, LoggedInUserDetails loggedInUserDetails) throws CAException;

	Boolean deleteUser(Long userId, LoggedInUserDetails loggedInUserDetails) throws CAException;

}
