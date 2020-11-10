package com.autobuds.PMDReportAggregator.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PMDReportAggregatorMainController {
	
	@GetMapping("test")
	public String test()
	{
		return "Hello World" ;
	}

}
