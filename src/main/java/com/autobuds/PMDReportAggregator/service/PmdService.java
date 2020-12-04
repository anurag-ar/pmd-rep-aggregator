package com.autobuds.PMDReportAggregator.service;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.autobuds.PMDReportAggregator.utility.ApexCode;
import com.autobuds.PMDReportAggregator.utility.PmdReport;
import com.autobuds.PMDReportAggregator.utility.PmdRequest;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RulesetsFactoryUtils;
import net.sourceforge.pmd.renderers.JsonRenderer;
import net.sourceforge.pmd.util.ClasspathClassLoader;
import net.sourceforge.pmd.util.datasource.DataSource;
import net.sourceforge.pmd.util.datasource.FileDataSource;

@Service
public class PmdService {

	private static PMDConfiguration configuration = new PMDConfiguration();

	private static RuleContext ctx = new RuleContext();

	private String base = "/unpackaged/classes/";

	public PmdReport generateReport(String email, PmdRequest pmdRequest) {
        
		String report="";
		PmdReport pmdReport = new PmdReport();
		List<ApexCode> apexCode = new ArrayList<>();
		try {
		List<DataSource> files = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		String filepath = "./userData/" + email + "/" + pmdRequest.getOrgId() + base;
		for (String fileName : pmdRequest.getApexClasses()) {
			File fl = new File(filepath + fileName) ;
			apexCode.add(new ApexCode(fileName, Files.readAllLines(fl.toPath()) ));
			files.add(new FileDataSource(fl));
		}
		if(pmdRequest.getRules()!=null && pmdRequest.getRules().size() > 0) {
		for (String rule : pmdRequest.getRules()) {
			sb.append("category/apex/").append(rule.trim()).append(",");
		}
		}
		else {
			sb = new StringBuilder();
		String rule = "category/apex/documentation.xml,category/apex/bestpractices.xml,"
				+ "category/apex/codestyle.xml,category/apex/design.xml," + "category/apex/errorprone.xml,"
				+ "category/apex/security.xml,category/apex/performance.xml,";
		sb.append(rule);
		}
		if(sb.length()>0) {
		sb.setLength(sb.length() - 1);
		configuration.setRuleSets(sb.toString());
		RuleSetFactory ruleSetFactory = RulesetsFactoryUtils.createFactory(configuration);
		report = runPmd(ruleSetFactory, files);
		pmdReport.setApexCode(apexCode);
		pmdReport.setReport(report);
		System.out.print(pmdReport);
		}
		return pmdReport;
		} catch(Exception ex) {
         throw new RuntimeException("Some Error in "+ex);
		}
	}

	private String runPmd(RuleSetFactory ruleSetFactory, List<DataSource> files) throws Exception {

		Writer rendererOutput = new StringWriter();
		JsonRenderer renderer = new JsonRenderer();
		try {
			renderer.setWriter(rendererOutput);
			renderer.start();
		    PMD.processFiles(configuration, ruleSetFactory, files, ctx, Collections.singletonList(renderer));
		    renderer.end();
			renderer.flush();
			return rendererOutput.toString();
		} catch(Exception ex) {
		   throw new RuntimeException(ex);
		}
	}

}
