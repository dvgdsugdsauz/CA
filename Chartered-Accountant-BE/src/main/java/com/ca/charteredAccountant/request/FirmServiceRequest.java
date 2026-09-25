package com.ca.charteredAccountant.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FirmServiceRequest {

	@Schema(description = "null to create, id to update")
	private Long serviceId;

	@Schema(description = "service_category id; null = uncategorised")
	private Long categoryId;

	@NotBlank(message = "Enter a title")
	@Size(max = 120, message = "Title can be up to 120 characters")
	private String title;

	@Schema(description = "Optional; derived from the title when blank, e.g. gst-registration")
	@Size(max = 120, message = "URL slug can be up to 120 characters")
	private String slug;

	@NotBlank(message = "Enter a short summary")
	@Size(max = 400, message = "Summary can be up to 400 characters")
	private String summary;

	private String body;

	@Size(max = 40, message = "Icon can be up to 40 characters")
	private String icon;

	@Size(max = 160, message = "Meta title can be up to 160 characters")
	private String metaTitle;

	@Size(max = 300, message = "Meta description can be up to 300 characters")
	private String metaDescription;

	private Boolean featured;

	private Boolean active;

	private Integer sortOrder;

	@Schema(description = "\"What is included\" bullets in display order; replaces the existing list")
	@Size(max = 20, message = "Add up to 20 highlights")
	private List<@NotBlank(message = "Highlight cannot be empty")
			@Size(max = 300, message = "Each highlight can be up to 300 characters") String> highlights;

}
