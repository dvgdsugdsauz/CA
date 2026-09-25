package com.ca.charteredAccountant.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private Jwt jwt = new Jwt();
	private Cors cors = new Cors();
	private Admin admin = new Admin();
	private Upload upload = new Upload();

	@Getter
	@Setter
	public static class Jwt {
		private String secret;
		private long expirationMinutes = 480;
	}

	@Getter
	@Setter
	public static class Cors {
		private List<String> allowedOrigins = new ArrayList<>();
	}

	@Getter
	@Setter
	public static class Admin {
		private String username;
		private String password;
		private String fullName;
		private String email;
	}

	@Getter
	@Setter
	public static class Upload {
		private String resumeDir = "uploads/resumes";
	}

}
