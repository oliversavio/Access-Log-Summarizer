package com.oliver.accesslogsummarizer.reports;

import com.oliver.accesslogsummarizer.beans.ReportContext;

public abstract class ReportWriter {

	public abstract Iterable<AccessLogReport> generateReport(ReportContext context);

}
