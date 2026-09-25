package com.ca.charteredAccountant.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.enums.ArticleStatus;
import com.ca.charteredAccountant.dao.model.ArticleEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.ArticleRepository;
import com.ca.charteredAccountant.request.ArticleRequest;
import com.ca.charteredAccountant.response.ArticleResponse;
import com.ca.charteredAccountant.response.ArticleSummaryResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.service.ArticleService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

	private static final String SLUG_TAKEN_MESSAGE = "This URL slug is already used by another article";
	private static final String SLUG_INVALID_MESSAGE = "Enter a title or slug with letters or numbers";

	private ArticleRepository articleRepository;

	// ========================= Public =========================

	@Override
	@Transactional(readOnly = true)
	public PageResponse<ArticleSummaryResponse> getPublishedArticles(String category, Pageable pageable) {
		return PageResponse.of(
				articleRepository.findPublished(ArticleStatus.PUBLISHED, LocalDateTime.now(),
						CommonUtil.trimToNull(category), pageable),
				this::mapEntityToSummaryResponse);
	}

	@Override
	@Transactional(readOnly = true)
	public ArticleResponse getPublishedArticleBySlug(String slug) {
		return articleRepository
				.findBySlugAndStatusAndPublishedAtLessThanEqual(slug, ArticleStatus.PUBLISHED, LocalDateTime.now())
				.map(this::mapEntityToResponse).orElse(null);
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public PageResponse<ArticleSummaryResponse> getAllArticles(ArticleStatus status, String search, Pageable pageable) {
		return PageResponse.of(
				articleRepository.searchArticles(status, CommonUtil.trimToNull(search), pageable),
				this::mapEntityToSummaryResponse);
	}

	// ========================= Get Single =========================

	@Override
	@Transactional(readOnly = true)
	public ArticleResponse getArticle(Long articleId) {
		return articleRepository.findById(articleId).map(this::mapEntityToResponse).orElse(null);
	}

	// ========================= Save (create / update) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveArticle(ArticleRequest request) throws CAException {

		ArticleEntity entity;
		if (request.getArticleId() == null) {
			entity = new ArticleEntity();
		} else {
			entity = articleRepository.findById(request.getArticleId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		String slug = CommonUtil.resolveSlug(request.getSlug(), request.getTitle());
		if (slug == null) {
			throw CAException.badRequest(SLUG_INVALID_MESSAGE);
		}
		boolean slugTaken = request.getArticleId() == null
				? articleRepository.existsBySlug(slug)
				: articleRepository.existsBySlugAndIdNot(slug, request.getArticleId());
		if (slugTaken) {
			throw CAException.conflict(SLUG_TAKEN_MESSAGE);
		}

		LocalDateTime publishedAt = request.getPublishedAt();
		if (request.getStatus() == ArticleStatus.PUBLISHED && publishedAt == null) {
			publishedAt = LocalDateTime.now();
		}

		entity.setTitle(request.getTitle().trim());
		entity.setSlug(slug);
		entity.setSummary(CommonUtil.trimToNull(request.getSummary()));
		entity.setBody(CommonUtil.trimToNull(request.getBody()));
		entity.setCategory(CommonUtil.trimToNull(request.getCategory()));
		entity.setAuthor(CommonUtil.trimToNull(request.getAuthor()));
		entity.setStatus(request.getStatus());
		entity.setPublishedAt(publishedAt);
		articleRepository.save(entity);
		return true;
	}

	// ========================= Delete =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteArticle(Long articleId) {
		if (!articleRepository.existsById(articleId)) {
			return false;
		}
		articleRepository.deleteById(articleId);
		log.info("Article {} deleted", articleId);
		return true;
	}

	// ========================= Mapping =========================

	private ArticleSummaryResponse mapEntityToSummaryResponse(ArticleEntity e) {
		ArticleSummaryResponse r = new ArticleSummaryResponse();
		fillSummary(r, e);
		return r;
	}

	private ArticleResponse mapEntityToResponse(ArticleEntity e) {
		ArticleResponse r = new ArticleResponse();
		fillSummary(r, e);
		r.setBody(e.getBody());
		r.setCreatedAt(e.getCreatedAt());
		r.setUpdatedAt(e.getUpdatedAt());
		return r;
	}

	private void fillSummary(ArticleSummaryResponse r, ArticleEntity e) {
		r.setArticleId(e.getId());
		r.setTitle(e.getTitle());
		r.setSlug(e.getSlug());
		r.setSummary(e.getSummary());
		r.setCategory(e.getCategory());
		r.setAuthor(e.getAuthor());
		r.setStatus(e.getStatus());
		r.setPublishedAt(e.getPublishedAt());
	}

}
