package com.autobuds.PMDReportAggregator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;
import org.springframework.web.servlet.view.RedirectView;

import com.autobuds.PMDReportAggregator.config.TokenConfig;
import com.autobuds.PMDReportAggregator.exception.InvalidCredentialsException;
import com.autobuds.PMDReportAggregator.model.Org;
import com.autobuds.PMDReportAggregator.model.OrgId;
import com.autobuds.PMDReportAggregator.service.SFOrgService;
import com.autobuds.PMDReportAggregator.utility.ApexResponse;

@RestController
@CrossOrigin
@RequestMapping("api")
public class OrgController {

	@Autowired
	private TokenConfig tokenConfig;

	@Autowired
	private SFOrgService orgService;

	@GetMapping(value = "/org")
	public ResponseEntity<?> getOrg(@RequestHeader("Authorization") String token) throws Exception {
		String email = tokenConfig.getUsernameFromToken(token.substring(7));
		List<Org> response = orgService.getOrgs(email);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/retrieve")
	public ResponseEntity<?> getapex(@RequestBody OrgId orgId,@RequestHeader("Authorization") String token) {
		try {
		String email = tokenConfig.getUsernameFromToken(token.substring(7));
		orgId.setUserId(email);
		List<String> list =  orgService.retrieve(orgId);
		 ApexResponse response = new ApexResponse();
		 response.setApexClasses(list);
         return ResponseEntity.ok(response);
	   } catch(Exception ex)
	   {
		throw new InvalidCredentialsException(ex.getMessage());		
	   }
	}
	
	

}
