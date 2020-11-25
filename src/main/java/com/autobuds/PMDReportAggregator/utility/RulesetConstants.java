package com.autobuds.PMDReportAggregator.utility;

import java.util.HashMap;
import java.util.Map;

public class RulesetConstants {

	public static String BASE_RULE_PATH = "category/apex/";
	public static String RULESETS = "category/apex/bestpractices.xml," + "category/apex/codestyle.xml,category/apex/design.xml,"
			+ "category/apex/documentation.xml,category/apex/errorprone.xml,"
			+ "category/apex/security.xml,category/apex/performance.xml";
	
	public static Map<String, String> DEFAULT_RULESET_PATHS;
	static {

		DEFAULT_RULESET_PATHS = new HashMap<String, String>();
		DEFAULT_RULESET_PATHS.put("Best Practices", "bestpractices.xml");
		DEFAULT_RULESET_PATHS.put("Code Style", "codestyle.xml");
		DEFAULT_RULESET_PATHS.put("Design", "design.xml");
		DEFAULT_RULESET_PATHS.put("Documentation", "documentation.xml");
		DEFAULT_RULESET_PATHS.put("Error Prone", "errorprone.xml");
		DEFAULT_RULESET_PATHS.put("Performance", "performance.xml");
		DEFAULT_RULESET_PATHS.put("Security", "security.xml");

	}
}
