package com.autobuds.PMDReportAggregator.utility;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApexCode {
	
	String className ;
	List<String> classCode ;

}
