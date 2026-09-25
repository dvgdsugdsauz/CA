package com.ca.charteredAccountant.service;

import org.springframework.data.domain.Pageable;

import com.ca.charteredAccountant.common.enums.ArticleStatus;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.ArticleRequest;
import com.ca.charteredAccountant.response.ArticleResponse;
import com.ca.charteredAccountant.response.ArticleSummaryResponse;
import com.ca.charteredAccountant.response.PageResponse;

public interface ArticleService {

	PageResponse<ArticleSummaryResponse> getPublishedArticles(String category, Pageable pageable);

	ArticleResponse getPublishedArticleBySlug(String slug);

	PageResponse<ArticleSummaryResponse> getAllArticles(ArticleStatus status, String search, Pageable pageable);

	ArticleResponse getArticle(Long articleId);

	Boolean saveArticle(ArticleRequest request) throws CAException;

	Boolean deleteArticle(Long articleId);

}
