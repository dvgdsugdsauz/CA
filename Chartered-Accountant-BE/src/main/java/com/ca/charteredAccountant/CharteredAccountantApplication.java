package com.ca.charteredAccountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CharteredAccountantApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharteredAccountantApplication.class, args);
	}

}
