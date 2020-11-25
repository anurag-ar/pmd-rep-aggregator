package com.autobuds.PMDReportAggregator.utility;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleRequest {
	
	@JsonProperty("rulesets")
	private List<String> defaultRulesets;
	
	@JsonProperty("rulesets")
	public List<String> getDefaultRulesets() {
		System.out.println(defaultRulesets);
		return defaultRulesets;
	}
	
	@JsonProperty("rulesets")
	public void setDefaultRulesets(List<String> defaultRulesets) {
		this.defaultRulesets = defaultRulesets;
	}

	@Override
	public String toString() {
		return "RuleRequest [defaultRulesets=" + defaultRulesets.toString() + "]";
	}
	
}
