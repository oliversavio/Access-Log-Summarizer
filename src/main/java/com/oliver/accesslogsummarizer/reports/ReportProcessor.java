package com.oliver.accesslogsummarizer.reports;

import com.oliver.accesslogsummarizer.beans.ReportContext;

public class ReportProcessor {

	private ReportWriter writer;
	
	public ReportProcessor(ReportWriter writer) {
		this.writer = writer;
	}
	
	public void generateReport(ReportContext context) {
		writer.generateReport(context);
	}
	
	
	
	
}
