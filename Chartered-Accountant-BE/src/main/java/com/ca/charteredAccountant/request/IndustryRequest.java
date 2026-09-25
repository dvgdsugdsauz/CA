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
public class IndustryRequest {

	@Schema(description = "null to create, id to update")
	private Long industryId;

	@NotBlank(message = "Enter an industry name")
	@Size(max = 80, message = "Name can be up to 80 characters")
	private String name;

	@Schema(description = "Optional; derived from the name when blank")
	@Size(max = 80, message = "URL slug can be up to 80 characters")
	private String slug;

	@Size(max = 300, message = "Summary can be up to 300 characters")
	private String summary;

	@Size(max = 40, message = "Icon can be up to 40 characters")
	private String icon;

	private Integer sortOrder;

}
