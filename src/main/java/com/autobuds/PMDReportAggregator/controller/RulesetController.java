package com.autobuds.PMDReportAggregator.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobuds.PMDReportAggregator.service.RulesetService;
import com.autobuds.PMDReportAggregator.utility.Rule;
import com.autobuds.PMDReportAggregator.utility.RuleListResponse;
import com.autobuds.PMDReportAggregator.utility.RuleRequest;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class RulesetController {
	
	@Autowired
	private RulesetService rulesetService;
	

	@PostMapping(value = "/rules", consumes = MediaType.APPLICATION_JSON_VALUE)
	public RuleListResponse getRulesForRuleset( @RequestBody RuleRequest ruleRequest){//@RequestHeader("Authorization") String token,
//		System.out.println(ruleRequest);
		List<Rule> ruleResponse = rulesetService.getRulesForRulesets(ruleRequest.getDefaultRulesets());
		RuleListResponse ruleListResponse = new RuleListResponse(ruleResponse);
//		System.out.println(ruleListResponse);
		return ruleListResponse;
	}
		
}
