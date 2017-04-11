package com.oliver.accesslogsummarizer;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;
import com.oliver.accesslogsummarizer.beans.ReportContext;
import com.oliver.accesslogsummarizer.helper.CommandLineArgumentParser;
import com.oliver.accesslogsummarizer.parser.AccessLogParser;
import com.oliver.accesslogsummarizer.parser.SimpleLogParser;
import com.oliver.accesslogsummarizer.reader.FileStream;
import com.oliver.accesslogsummarizer.reader.InputStream;
import com.oliver.accesslogsummarizer.reports.DefaultReportWriter;
import com.oliver.accesslogsummarizer.reports.QuickSummaryReportWriter;
import com.oliver.accesslogsummarizer.reports.ReportProcessor;
import com.oliver.accesslogsummarizer.reports.ReportWriter;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	public void processAccessLog(ParsingOptions options) {

		Map<String, Metric> metricMap = parseAccessLogAndGenerateMetricMap(options)
				.orElseThrow(() -> new IllegalStateException("Parsing failed"));

		ReportContext context = makeReportContext(metricMap, options);

		generateReport(context, options);

	}

	public static void main(String[] args) {

		if (args.length < 4) {
			throw new IllegalArgumentException("Not Enough Arguments Passed to the method");
		}

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);

		Main r = new Main();
		r.processAccessLog(options);

	}

	private void generateReport(ReportContext context, ParsingOptions options) {
		ReportProcessor report = new ReportProcessor(getReportWriterStrategy(options.getReportType()));
		report.generateReport(context);
	}

	private ReportContext makeReportContext(Map<String, Metric> metricMap, ParsingOptions options) {
		ReportContext context = new ReportContext();
		context.setMetricMap(metricMap);
		context.setTimeFactor(options.getTimeFactor());
		context.setContainsTimeParam(options.isContainsTimeValue());

		return context;
	}

	private Optional<Map<String, Metric>> parseAccessLogAndGenerateMetricMap(ParsingOptions options) {
		AccessLogParser parser = new SimpleLogParser();
		InputStream<String> input = new FileStream();
		Map<String, Metric> metricMap = null;

		metricMap = parser.parseLog(input.getStream(options.getFileName()), options);
		
		return Optional.ofNullable(metricMap);
	}

	private ReportWriter getReportWriterStrategy(ReportType reportType) {

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
