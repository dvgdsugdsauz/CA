package com.ca.charteredAccountant.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.ca.charteredAccountant.exception.CAException;

public interface FileStorageService {

	/** Stores the file under the upload base dir and returns its path relative to that dir. */
	String store(MultipartFile file, String subFolder) throws CAException;

	/** The stored file, or null if the path is missing or outside the upload dir. */
	Resource load(String relativePath);

}
