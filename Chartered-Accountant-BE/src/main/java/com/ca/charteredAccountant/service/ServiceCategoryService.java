package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.ServiceCategoryRequest;
import com.ca.charteredAccountant.response.ServiceCategoryResponse;

public interface ServiceCategoryService {

	List<ServiceCategoryResponse> getAllCategories();

	Boolean saveCategory(ServiceCategoryRequest request) throws CAException;

	Boolean deleteCategory(Long categoryId) throws CAException;

}
