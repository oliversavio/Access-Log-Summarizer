package com.oliver.accesslogsummarizer.helper;

import java.util.List;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.beans.ReportContext;
import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;
import com.oliver.accesslogsummarizer.reports.DefaultReportWriter;
import com.oliver.accesslogsummarizer.reports.QuickSummaryReportWriter;
import com.oliver.accesslogsummarizer.reports.ReportProcessor;
import com.oliver.accesslogsummarizer.reports.ReportWriter;

public class ReportHelper {

	private ReportHelper() {

	}

	public static void MakeReport(List<Metric> metrics, ParsingOptions options) {
		ReportContext context = new ReportContext();
		context.setMetrics(metrics);
		context.setTimeFactor(options.getTimeFactor());
		context.setContainsTimeParam(options.isContainsTimeValue());
		
		ReportProcessor report = new ReportProcessor(getReportWriterStrategy(context.getReportType()));
		report.generateReport(context);
	}

	
	private static ReportWriter getReportWriterStrategy(ReportType reportType) {

		if (reportType == null) {
			return new DefaultReportWriter();
		}

		switch (reportType) {
		case QUICK_SUMMARY:
			return new QuickSummaryReportWriter();
		case DEFAULT:
		default:
			return new DefaultReportWriter();
		}

	}
	
}
