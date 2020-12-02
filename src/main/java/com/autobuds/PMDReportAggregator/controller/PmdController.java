package com.autobuds.PMDReportAggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobuds.PMDReportAggregator.config.TokenConfig;
import com.autobuds.PMDReportAggregator.service.PmdService;
import com.autobuds.PMDReportAggregator.utility.PmdReport;
import com.autobuds.PMDReportAggregator.utility.PmdRequest;

@RestController
@CrossOrigin
@RequestMapping("api")
public class PmdController {
	
	@Autowired
	private TokenConfig tokenConfig;
	
	@Autowired
	private PmdService pmdService ;
	
	@PostMapping("/report")
	public ResponseEntity<?> generatePmdReport(@RequestHeader("Authorization") String token ,@RequestBody PmdRequest pmdRequest) throws Exception{
		
		String email = tokenConfig.getUsernameFromToken(token.substring(7));
		PmdReport pmdReport = pmdService.generateReport(email, pmdRequest) ;
		//System.out.println(pmdReport.getReport());
		//System.out.println(pmdReport.getApexCode());
		return ResponseEntity.ok(pmdReport);
	}


}
