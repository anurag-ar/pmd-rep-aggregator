package com.autobuds.PMDReportAggregator.utility;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rule implements Serializable {

	private String ruleName;
	private String rulePath;
	private boolean isDeprecated;
	private String priorityLabel;
	private int priority;
	
}
