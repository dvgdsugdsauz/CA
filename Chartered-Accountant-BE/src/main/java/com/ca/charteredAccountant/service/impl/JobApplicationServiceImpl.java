package com.ca.charteredAccountant.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.enums.ApplicationStatus;
import com.ca.charteredAccountant.dao.model.JobApplicationEntity;
import com.ca.charteredAccountant.dao.model.JobOpeningEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.JobApplicationRepository;
import com.ca.charteredAccountant.repository.JobOpeningRepository;
import com.ca.charteredAccountant.request.JobApplicationRequest;
import com.ca.charteredAccountant.request.JobApplicationStatusRequest;
import com.ca.charteredAccountant.response.JobApplicationResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.service.FileStorageService;
import com.ca.charteredAccountant.service.JobApplicationService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

	private static final String RESUME_NOT_FOUND_MESSAGE = "Resume not found";
	private static final DateTimeFormatter SUB_FOLDER_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM");

	private JobApplicationRepository jobApplicationRepository;
	private JobOpeningRepository jobOpeningRepository;
	private FileStorageService fileStorageService;

	// ========================= Apply (public website) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void applyForJob(JobApplicationRequest request, MultipartFile resume) throws CAException {

		LocalDate today = LocalDate.now();

		JobOpeningEntity job = jobOpeningRepository.findById(request.getJobId())
				.filter(j -> Boolean.TRUE.equals(j.getActive()))
				.filter(j -> j.getClosesOn() == null || !j.getClosesOn().isBefore(today))
				.orElseThrow(() -> CAException.badRequest(Constants.ResponseMessages.JOB_CLOSED_MESSAGE));

		validateResume(resume);

		String resumePath = fileStorageService.store(resume, today.format(SUB_FOLDER_FORMAT));

		JobApplicationEntity entity = JobApplicationEntity.builder()
				.job(job)
				.fullName(request.getFullName().trim())
				.email(CommonUtil.normalizeEmail(request.getEmail()))
				.phone(request.getPhone().trim())
				.qualification(CommonUtil.trimToNull(request.getQualification()))
				.coverNote(CommonUtil.trimToNull(request.getCoverNote()))
				.resumePath(resumePath)
				.status(ApplicationStatus.RECEIVED)
				.build();

		JobApplicationEntity saved = jobApplicationRepository.save(entity);
		log.info("Job application {} received for job {}", saved.getId(), job.getId());
	}

	private void validateResume(MultipartFile resume) throws CAException {
		if (resume == null || resume.isEmpty() || resume.getSize() > Constants.MAX_RESUME_SIZE_BYTES) {
			throw CAException.badRequest(Constants.ResponseMessages.INVALID_FILE_MESSAGE);
		}
		String extension = StringUtils.getFilenameExtension(resume.getOriginalFilename());
		if (extension == null || Arrays.stream(Constants.ALLOWED_RESUME_EXTENSIONS).noneMatch(extension::equalsIgnoreCase)) {
			throw CAException.badRequest(Constants.ResponseMessages.INVALID_FILE_MESSAGE);
		}
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public PageResponse<JobApplicationResponse> getAllApplications(Long jobId, ApplicationStatus status,
			Pageable pageable) {
		return PageResponse.of(jobApplicationRepository.searchApplications(jobId, status, pageable),
				this::mapEntityToResponse);
	}

	// ========================= Update Status =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateApplicationStatus(JobApplicationStatusRequest request) {
		JobApplicationEntity entity = jobApplicationRepository.findById(request.getApplicationId()).orElse(null);
		if (entity == null) {
			return false;
		}
		if (entity.getStatus() != request.getStatus()) {
			log.info("Job application {} status {} -> {}", entity.getId(), entity.getStatus(), request.getStatus());
		}
		entity.setStatus(request.getStatus());
		jobApplicationRepository.save(entity);
		return true;
	}

	// ========================= Download Resume =========================

	@Override
	@Transactional(readOnly = true)
	public ResumeFile getResume(Long applicationId) throws CAException {

		JobApplicationEntity entity = jobApplicationRepository.findById(applicationId)
				.orElseThrow(() -> CAException.notFound(RESUME_NOT_FOUND_MESSAGE));
		if (!StringUtils.hasText(entity.getResumePath())) {
			throw CAException.notFound(RESUME_NOT_FOUND_MESSAGE);
		}

		Resource resource = fileStorageService.load(entity.getResumePath());
		if (resource == null) {
			log.warn("Resume file missing for application {}: {}", applicationId, entity.getResumePath());
			throw CAException.notFound(RESUME_NOT_FOUND_MESSAGE);
		}

		String extension = StringUtils.getFilenameExtension(entity.getResumePath());
		extension = extension != null ? extension.toLowerCase() : "";

		String baseName = entity.getFullName() != null ? entity.getFullName().replaceAll("[^A-Za-z0-9]", "_") : "applicant";
		String fileName = baseName + "-resume" + (extension.isEmpty() ? "" : "." + extension);

		return new ResumeFile(resource, fileName, resolveContentType(extension));
	}

	private String resolveContentType(String extension) {
		return switch (extension) {
		case "pdf" -> MediaType.APPLICATION_PDF_VALUE;
		case "doc" -> "application/msword";
		case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
		};
	}

	// ========================= Mapping =========================

	private JobApplicationResponse mapEntityToResponse(JobApplicationEntity e) {
		JobApplicationResponse r = new JobApplicationResponse();
		r.setApplicationId(e.getId());
		r.setJobId(e.getJob() != null ? e.getJob().getId() : null);
		r.setJobTitle(e.getJob() != null ? e.getJob().getTitle() : null);
		r.setFullName(e.getFullName());
		r.setEmail(e.getEmail());
		r.setPhone(e.getPhone());
		r.setQualification(e.getQualification());
		r.setCoverNote(e.getCoverNote());
		r.setStatus(e.getStatus());
		r.setStatusLabel(e.getStatus() != null ? e.getStatus().getLabel() : null);
		r.setHasResume(StringUtils.hasText(e.getResumePath()));
		r.setCreatedAt(e.getCreatedAt());
		return r;
	}

}
