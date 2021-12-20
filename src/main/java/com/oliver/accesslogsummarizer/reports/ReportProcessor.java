package com.oliver.accesslogsummarizer.reports;

import com.oliver.accesslogsummarizer.beans.ReportContext;

public class ReportProcessor {

	private ReportWriter writer;

	public ReportProcessor(ReportWriter writer) {
		this.writer = writer;
	}

	public Iterable<AccessLogReport> getReports(ReportContext context) {
		return this.writer.generateReport(context);
	}

	
}
