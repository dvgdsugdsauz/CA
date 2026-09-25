package com.ca.charteredAccountant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestimonialRequest {

	@Schema(description = "Null to create, existing id to update")
	private Long testimonialId;

	@NotBlank(message = "Enter the client's name")
	@Size(max = 120, message = "Name can be up to 120 characters")
	private String authorName;

	@Size(max = 160, message = "Title can be up to 160 characters")
	private String authorTitle;

	@NotBlank(message = "Enter the testimonial")
	@Size(max = 2000, message = "Testimonial can be up to 2000 characters")
	private String quote;

	@Min(value = 1, message = "Rating must be between 1 and 5")
	@Max(value = 5, message = "Rating must be between 1 and 5")
	private Integer rating;

	private Boolean active;

	private Integer sortOrder;

}
