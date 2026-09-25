package com.ca.charteredAccountant.request;

import java.time.LocalDateTime;

import com.ca.charteredAccountant.common.enums.ArticleStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleRequest {

	@Schema(description = "null to create, id to update")
	private Long articleId;

	@NotBlank(message = "Enter a title")
	@Size(max = 200, message = "Title can be up to 200 characters")
	private String title;

	@Schema(description = "Optional; derived from the title when blank")
	@Size(max = 200, message = "Slug can be up to 200 characters")
	private String slug;

	@Size(max = 500, message = "Summary can be up to 500 characters")
	private String summary;

	@Size(max = 60000, message = "Article body can be up to 60000 characters")
	private String body;

	@Size(max = 60, message = "Category can be up to 60 characters")
	private String category;

	@Size(max = 120, message = "Author can be up to 120 characters")
	private String author;

	@NotNull(message = "Choose draft or published")
	private ArticleStatus status;

	@Schema(description = "Optional; a future time schedules the article. Defaults to now when publishing.")
	private LocalDateTime publishedAt;

}
