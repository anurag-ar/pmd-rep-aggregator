package com.autobuds.PMDReportAggregator.utility;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RulepathResponse {

	@JsonProperty("rulepaths")
	private List<String> rulePaths;
	
	@JsonProperty("rulepaths")
	public List<String> getRulePaths() {
		return rulePaths;
	}
	
	@JsonProperty("rulepaths")
	public void setRulePaths(List<String> rulePaths) {
		this.rulePaths = rulePaths;
	}

	@Override
	public String toString() {
		return "RulepathResponse [rulePaths=" + rulePaths + "]";
	}
	
	
}
