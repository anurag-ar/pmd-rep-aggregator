package com.autobuds.PMDReportAggregator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobuds.PMDReportAggregator.utility.Rule;
import com.autobuds.PMDReportAggregator.utility.RulesetConstants;

@Service
public class RulesetService {
	
	public List<Rule> getRulesForRulesets(List<String> requestRulesets){
		Map<String,List<Rule>> rulesetRuleMap = ApexPMDService.rulesetRuleMapping;
		List<Rule> rules = new ArrayList<Rule>();
		for(String ruleset: requestRulesets) {
			rules.addAll(rulesetRuleMap.get(ruleset));
		}
//		System.out.println(rules);
		return rules;
	}
	
	
}
