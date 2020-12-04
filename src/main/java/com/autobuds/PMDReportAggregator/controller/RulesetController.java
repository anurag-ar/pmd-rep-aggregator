package com.autobuds.PMDReportAggregator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobuds.PMDReportAggregator.config.TokenConfig;
import com.autobuds.PMDReportAggregator.service.RulesetService;
import com.autobuds.PMDReportAggregator.utility.Rule;
import com.autobuds.PMDReportAggregator.utility.RuleListResponse;
import com.autobuds.PMDReportAggregator.utility.RuleRequest;

@RestController
@CrossOrigin
@RequestMapping("api")
public class RulesetController {
	
	@Autowired
	private RulesetService rulesetService;
	
	@Autowired
	private TokenConfig tokenConfig;
	

	@PostMapping(value = "/auth/rules", consumes = MediaType.APPLICATION_JSON_VALUE)
	public RuleListResponse getRulesForRuleset( @RequestBody RuleRequest ruleRequest){//@RequestHeader("Authorization") String token,
//		System.out.println(ruleRequest);
		List<Rule> ruleResponse = rulesetService.getRulesForRulesets(ruleRequest.getDefaultRulesets());
		RuleListResponse ruleListResponse = new RuleListResponse(ruleResponse);
//		System.out.println(ruleListResponse);
		return ruleListResponse;
	}
	
	@PostMapping("/rules")
	public ResponseEntity<?> saveRules(@RequestBody RuleRequest ruleRequest,
	@RequestHeader("Authorization") String token){
		System.out.println();
		String email = tokenConfig.getUsernameFromToken(token.substring(7));
		System.out.println(email + " "+ ruleRequest);
		rulesetService.saveRules(ruleRequest.getDefaultRulesets(), email);
		Map<String, Object> response = new HashMap<>();
		response.put("message","Saved Successfully");
		return ResponseEntity.ok(response);
		
	}
		
}
