package com.autobuds.PMDReportAggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobuds.PMDReportAggregator.config.TokenConfig;
import com.autobuds.PMDReportAggregator.service.PmdService;
import com.autobuds.PMDReportAggregator.utility.PmdRequest;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class PmdController {
	
	@Autowired
	private TokenConfig tokenConfig;
	
	@Autowired
	private PmdService pmdService ;
	
	@PostMapping("/pmd")
	public ResponseEntity<?> generatePmdReport(@RequestBody PmdRequest pmdRequest) throws Exception{
		
		pmdService.generateReport("safaf@dads.com", pmdRequest);
		return ResponseEntity.ok("");
	}


}
