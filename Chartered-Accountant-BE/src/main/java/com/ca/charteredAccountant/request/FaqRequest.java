package com.ca.charteredAccountant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaqRequest {

	@Schema(description = "Null to create, existing id to update")
	private Long faqId;

	@NotBlank(message = "Enter the question")
	@Size(max = 300, message = "Question can be up to 300 characters")
	private String question;

	@NotBlank(message = "Enter the answer")
	@Size(max = 5000, message = "Answer can be up to 5000 characters")
	private String answer;

	@Schema(description = "office_location id; null = shown on every city page")
	private Long locationId;

	@Schema(description = "firm_service id; null = general FAQ")
	private Long serviceId;

	private Boolean active;

	private Integer sortOrder;

}
