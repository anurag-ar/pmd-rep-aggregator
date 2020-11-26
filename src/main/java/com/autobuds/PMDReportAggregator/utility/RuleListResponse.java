package com.autobuds.PMDReportAggregator.utility;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuleListResponse {
	
	private List<Rule> rules;
	
	
}
