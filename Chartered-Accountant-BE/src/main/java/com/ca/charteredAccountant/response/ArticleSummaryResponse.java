package com.ca.charteredAccountant.response;

import java.time.LocalDateTime;

import com.ca.charteredAccountant.common.enums.ArticleStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleSummaryResponse {

	private Long articleId;
	private String title;
	private String slug;
	private String summary;
	private String category;
	private String author;
	private ArticleStatus status;
	private LocalDateTime publishedAt;

}
