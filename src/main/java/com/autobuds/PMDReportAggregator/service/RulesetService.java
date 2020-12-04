package com.autobuds.PMDReportAggregator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobuds.PMDReportAggregator.exception.InvalidCredentialsException;
import com.autobuds.PMDReportAggregator.model.Ruleset;
import com.autobuds.PMDReportAggregator.model.RulesetId;
import com.autobuds.PMDReportAggregator.repository.RulesetRepository;
import com.autobuds.PMDReportAggregator.utility.Rule;
import com.autobuds.PMDReportAggregator.utility.RulesetConstants;

@Service
public class RulesetService {
	
	@Autowired
	private RulesetRepository ruleRepo ;
	
	public List<Rule> getRulesForRulesets(List<String> requestRulesets){
		Map<String,List<Rule>> rulesetRuleMap = ApexPMDService.rulesetRuleMapping;
		List<Rule> rules = new ArrayList<Rule>();
		if(requestRulesets !=null) {
		for(String ruleset: requestRulesets) {
			rules.addAll(rulesetRuleMap.get(ruleset));
		}
		}
//		System.out.println(rules);
		return rules;
	}
	
	public void saveRules(List<String> rules,String email) {
		try {
		RulesetId rulesetId = new RulesetId();
		rulesetId.setRulesetName("rule"); rulesetId.setUserId(email);
		Ruleset ruleset = new Ruleset();
		ruleset.setRulesetId(rulesetId);
		String rule = rules.toString();
		ruleset.setRules(rule.substring(1, rule.length()-1));
		ruleRepo.save(ruleset);
		} catch(Exception ex)
		{
			throw new InvalidCredentialsException("Some error"+ ex);
		}
	}
	
	
	
	
}
