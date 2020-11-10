package com.autobuds.PMDReportAggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@Lazy
public class PmdReportAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmdReportAggregatorApplication.class, args);
	}

}
