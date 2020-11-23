package com.autobuds.PMDReportAggregator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;
import org.springframework.web.servlet.view.RedirectView;

import com.autobuds.PMDReportAggregator.config.TokenConfig;
import com.autobuds.PMDReportAggregator.model.Org;
import com.autobuds.PMDReportAggregator.service.SFOrgService;

@RestController
@CrossOrigin
@RequestMapping("api")
public class OrgController {
     
	@Autowired
	private TokenConfig tokenConfig ;
	
	@Autowired
	private SFOrgService orgService ;
	   
	@GetMapping(value = "/org")
	public ResponseEntity<?> getOrg(@RequestHeader("Authorization") String token) throws Exception {
		String email = tokenConfig.getUsernameFromToken(token.substring(7));
          List<Org> response = orgService.getOrgs(email);
		 return ResponseEntity.ok(response) ;
	}
}
