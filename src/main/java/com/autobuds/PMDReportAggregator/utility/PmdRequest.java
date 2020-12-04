package com.autobuds.PMDReportAggregator.utility;

import java.util.List;

import lombok.Data;

@Data
public class PmdRequest {
	
	private List<String> apexClasses ;
	
	private List<String> rules ;
	
	private String orgId ;
	
	private List<String> mailList;

}
