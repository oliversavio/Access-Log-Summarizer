package com.oliver.accesslogsummarizer.helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.beans.ReportContext;
import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;
import com.oliver.accesslogsummarizer.reports.AccessLogReport;
import com.oliver.accesslogsummarizer.reports.DefaultReportWriter;
import com.oliver.accesslogsummarizer.reports.HtmlTableBuilder;
import com.oliver.accesslogsummarizer.reports.QuickSummaryReportWriter;
import com.oliver.accesslogsummarizer.reports.ReportProcessor;
import com.oliver.accesslogsummarizer.reports.ReportWriter;

public class ReportHelper {

	private static final Logger logger = LogManager.getLogger(ReportHelper.class);

	private ReportHelper() {

	}

	public static void MakeHtmlReport(List<Metric> metrics, ParsingOptions options) {
		ReportContext context = new ReportContext(options);
		context.setMetrics(metrics);
	
		ReportProcessor reportProcessor = new ReportProcessor(getReportWriterStrategy(context.getReportType()));
		Iterable<AccessLogReport> reports = reportProcessor.getReports(context);
	
		StringBuilder sb = new StringBuilder();
		reports.forEach(report -> {
			HtmlTableBuilder builder = new HtmlTableBuilder();
			builder
			.caption(report.getCaption())
			.header(report.getColumnHeaderMap())
			.body(report.getRows());
			
			sb.append(builder.toString());
		});
	
		writeToFile(sb.toString(), "AccessLog_Report.html");
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

	static void writeToFile(String result, String fileName) {
		Path path = Paths.get(fileName);
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(result);
		} catch (IOException ex) {
			logger.error("Error while writing file to output: ", ex);
		}
	}

}
