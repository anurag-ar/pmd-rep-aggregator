package com.autobuds.PMDReportAggregator.utility;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailField {
	private String[] recipients;
	private String userEmail;
	private String orgId;
	private String reportString;
}
