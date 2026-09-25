package com.ca.charteredAccountant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	private static final String BEARER_SCHEME = "bearerAuth";

	@Bean
	public OpenAPI charteredAccountantOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Chartered Accountant API")
						.description("Public website API (/api/public) and back office API (/api/admin). "
								+ "Sign in with /api/auth/login and use Authorize with the returned token.")
						.version("v1"))
				.components(new Components().addSecuritySchemes(BEARER_SCHEME,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME));
	}

}
