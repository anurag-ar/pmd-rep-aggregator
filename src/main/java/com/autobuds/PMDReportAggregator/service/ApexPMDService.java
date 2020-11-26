package com.autobuds.PMDReportAggregator.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.autobuds.PMDReportAggregator.utility.RulesetConstants;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RuleSetNotFoundException;
import net.sourceforge.pmd.RulesetsFactoryUtils;
//import com.autobuds.PMDReportAggregator.utility.Rule;

@Service
public class ApexPMDService {
	public static Map<String,List<com.autobuds.PMDReportAggregator.utility.Rule>> rulesetRuleMapping;
	
	static {
		rulesetRuleMapping = createRulesetRuleMap(RulesetConstants.RULESETS);
	}
	
	public static Map<String,List<com.autobuds.PMDReportAggregator.utility.Rule>> createRulesetRuleMap(String rulesets) {
		Map<String,List<com.autobuds.PMDReportAggregator.utility.Rule>> ruleMap = new HashMap<>();
		PMDConfiguration configuration = new PMDConfiguration();
		configuration.setRuleSets(rulesets);
		RuleSetFactory rf = RulesetsFactoryUtils.createFactory(configuration);

		Iterator<RuleSet> it;
		try {
			it = rf.getRegisteredRuleSets();
			while (it.hasNext()) {
				RuleSet rs = it.next();
				List<com.autobuds.PMDReportAggregator.utility.Rule> li = new ArrayList<>();

				Collection<Rule> cl = rs.getRules();
				Iterator<Rule> is = cl.iterator();
				
				while (is.hasNext()) {
					Rule rul = is.next();
					String rulePath = RulesetConstants.DEFAULT_RULESET_PATHS.get(rs.getName()) + "/" + rul.getName();//rul.getRuleSetName()
					com.autobuds.PMDReportAggregator.utility.Rule customRule 
					= new com.autobuds.PMDReportAggregator.utility.Rule(rul.getName(), rulePath, rs.getName(), rul.isDeprecated(), rul.getPriority().toString(), rul.getPriority().getPriority());
					li.add(customRule);
				}
				ruleMap.put(rs.getName(), li);
			}
		} catch (RuleSetNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ruleMap;
	}
	
}
