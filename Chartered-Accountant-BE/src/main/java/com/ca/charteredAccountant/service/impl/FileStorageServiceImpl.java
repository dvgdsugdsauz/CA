package com.ca.charteredAccountant.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.config.AppProperties;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.service.FileStorageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

	private AppProperties appProperties;

	// ========================= Store =========================

	@Override
	public String store(MultipartFile file, String subFolder) throws CAException {

		Path baseDir = getBaseDir();
		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String fileName = UUID.randomUUID() + (extension != null ? "." + extension.toLowerCase() : "");

		Path folder = StringUtils.hasText(subFolder) ? baseDir.resolve(subFolder).normalize() : baseDir;
		Path target = folder.resolve(fileName).normalize();
		if (!target.startsWith(baseDir)) {
			log.warn("Blocked upload outside the resume dir: {}", subFolder);
			throw new CAException(Constants.INTERNAL_SERVER_ERROR, Constants.ResponseMessages.UNABLE_SAVE_MESSAGE);
		}

		try (InputStream in = file.getInputStream()) {
			Files.createDirectories(folder);
			Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error("Unable to store file {}", target, e);
			throw new CAException(Constants.INTERNAL_SERVER_ERROR, Constants.ResponseMessages.UNABLE_SAVE_MESSAGE);
		}

		return baseDir.relativize(target).toString().replace('\\', '/');
	}

	// ========================= Load =========================

	@Override
	public Resource load(String relativePath) {

		if (!StringUtils.hasText(relativePath)) {
			return null;
		}

		Path baseDir = getBaseDir();
		Path file;
		try {
			file = baseDir.resolve(relativePath).normalize();
		} catch (InvalidPathException e) {
			log.warn("Invalid stored file path {}", relativePath);
			return null;
		}

		if (!file.startsWith(baseDir) || !Files.isRegularFile(file) || !Files.isReadable(file)) {
			return null;
		}
		return new PathResource(file);
	}

	private Path getBaseDir() {
		return Paths.get(appProperties.getUpload().getResumeDir()).toAbsolutePath().normalize();
	}

}
