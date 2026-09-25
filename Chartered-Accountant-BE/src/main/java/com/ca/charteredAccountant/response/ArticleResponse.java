package com.ca.charteredAccountant.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleResponse extends ArticleSummaryResponse {

	private String body;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
