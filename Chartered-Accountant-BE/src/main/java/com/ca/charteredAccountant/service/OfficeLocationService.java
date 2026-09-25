package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.OfficeLocationRequest;
import com.ca.charteredAccountant.response.OfficeLocationResponse;

public interface OfficeLocationService {

	List<OfficeLocationResponse> getAllActiveLocations();

	OfficeLocationResponse getLocationBySlug(String slug);

	List<OfficeLocationResponse> getAllLocations();

	OfficeLocationResponse getLocation(Long locationId);

	Boolean saveLocation(OfficeLocationRequest request) throws CAException;

	Boolean deleteLocation(Long locationId);

}
