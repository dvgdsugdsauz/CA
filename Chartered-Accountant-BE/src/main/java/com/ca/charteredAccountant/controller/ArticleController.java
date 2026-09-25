package com.ca.charteredAccountant.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.URLConstants;
import com.ca.charteredAccountant.common.enums.ArticleStatus;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.ArticleRequest;
import com.ca.charteredAccountant.response.ArticleResponse;
import com.ca.charteredAccountant.response.ArticleSummaryResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Article Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class ArticleController extends BaseController {

	private ArticleService articleService;

//	=========================== Get Published Articles (public) ========================

	@Operation(
			summary = "Get Published Articles",
			description = "Paged list of published articles whose publish time has passed, newest first. "
					+ "Optionally filter by exact category."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.FETCH_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@GetMapping(URLConstants.Article.GET_PUBLISHED_ARTICLES)
	public ResponseEntity<Response> getPublishedArticles(
			@RequestParam(required = false) String category,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		Response response = null;

		PageResponse<ArticleSummaryResponse> articles = articleService.getPublishedArticles(category,
				buildPageable(page, size, Sort.by(Sort.Direction.DESC, "publishedAt")));

		if (articles.getTotalElements() > 0) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, articles);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, articles);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Article by Slug (public) ========================

	@Operation(summary = "Get Article by Slug", description = "Full detail of one published article.")
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.FETCH_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@GetMapping(URLConstants.Article.GET_ARTICLE_BY_SLUG)
	public ResponseEntity<Response> getArticleBySlug(@PathVariable String slug) {

		Response response = null;

		ArticleResponse article = articleService.getPublishedArticleBySlug(slug);

		if (article != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, article);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All Articles (back office) ========================

	@Operation(
			summary = "Get All Articles",
			description = "Paged list of all articles, newest first. Filter by status and/or search on title."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.FETCH_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@GetMapping(URLConstants.Article.GET_ALL_ARTICLES)
	public ResponseEntity<Response> getAllArticles(
			@RequestParam(required = false) ArticleStatus status,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		Response response = null;

		PageResponse<ArticleSummaryResponse> articles = articleService.getAllArticles(status, search,
				buildPageable(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));

		if (articles.getTotalElements() > 0) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, articles);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, articles);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Single Article (back office) ========================

	@Operation(summary = "Get Article by ID", description = "Full detail of one article in any status.")
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.FETCH_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@GetMapping(URLConstants.Article.GET_ARTICLE_BY_ID)
	public ResponseEntity<Response> getArticle(@PathVariable Long articleId) {

		Response response = null;

		ArticleResponse article = articleService.getArticle(articleId);

		if (article != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, article);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Article ========================

	@Operation(
			summary = "Save Article",
			description = "Creates an article when articleId is null, otherwise updates it. Publishing without "
					+ "publishedAt publishes now; a future publishedAt schedules the article."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.SAVE_MESSAGE + " / " + Constants.ResponseMessages.UPDATE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.CONFLICT + "",
			description = "This URL slug is already used by another article",
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.Article.SAVE_ARTICLE)
	public ResponseEntity<Response> saveArticle(@Validated @RequestBody ArticleRequest request) throws CAException {

		Response response = null;

		Boolean isSaved = articleService.saveArticle(request);

		if (Boolean.TRUE.equals(isSaved)) {
			response = new Response(Constants.OK, request.getArticleId() == null
					? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Article ========================

	@Operation(summary = "Delete Article", description = "Permanently deletes an article.")
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.DELETED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.Article.DELETE_ARTICLE)
	public ResponseEntity<Response> deleteArticle(@RequestParam(name = "articleId") Long articleId) {

		Response response = null;

		Boolean isDeleted = articleService.deleteArticle(articleId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}
