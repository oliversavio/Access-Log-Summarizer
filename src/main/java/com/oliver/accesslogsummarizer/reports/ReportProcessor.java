package com.oliver.accesslogsummarizer.reports;

import java.util.Map;

import com.oliver.accesslogsummarizer.beans.Metric;

public class ReportProcessor {

	
	private ReportWriter writer;
	
	public ReportProcessor(ReportWriter writer) {
		this.writer = writer;
	}
	
	public void generateReport(Map<String, Metric> metriMap) {
		writer.generateReport(metriMap);
	}
	
	
	
	
}
