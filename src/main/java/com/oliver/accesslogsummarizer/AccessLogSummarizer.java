package com.oliver.accesslogsummarizer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;
import com.oliver.accesslogsummarizer.beans.ReportContext;
import com.oliver.accesslogsummarizer.helper.CommandLineArgumentParser;
import com.oliver.accesslogsummarizer.reports.DefaultReportWriter;
import com.oliver.accesslogsummarizer.reports.QuickSummaryReportWriter;
import com.oliver.accesslogsummarizer.reports.ReportProcessor;
import com.oliver.accesslogsummarizer.reports.ReportWriter;

public class AccessLogSummarizer {

	private static final Logger logger = LogManager.getLogger(AccessLogSummarizer.class);

	public void processAccessLog(ParsingOptions options) {
		AccessLogParser parser = new SimpleLogParser();
		Map<String, Metric> metricMap = null;
		logger.info("Processing..");
		long s1 = System.currentTimeMillis();
		
		try(Stream<String> stream = Files.lines(Paths.get(options.getFileName()), Charset.forName("Cp1252"))) {
			metricMap = parser.parseLog(stream, options);
		} catch (IOException e) {
			logger.error("Problem Streaming File: ", e);
			return;
		}
		
		ReportContext context = new ReportContext();
		context.setMetricMap(metricMap);
		context.setTimeFactor(options.getTimeFactor());
		context.setContainsTimeParam(options.isContainsTimeValue());
		
		ReportProcessor report = new ReportProcessor(getReportWriterStrategy(options.getReportType()));
		report.generateReport(context);
		
		long e1 = System.currentTimeMillis();
		
		logger.info("Done!");
		logger.info("Processing Time(s): " + (e1-s1)/1000d);
	}
	
	
	private ReportWriter getReportWriterStrategy(ReportType reportType) {
		
		if(reportType == null) {
			return new DefaultReportWriter();
		}
		
		switch(reportType) {
			case QUICK_SUMMARY : return new QuickSummaryReportWriter();
			case DEFAULT : 
			default : return new DefaultReportWriter();
		}
		
	}
	
	
	
	
public static void main(String[] args) {
		
		if(args.length < 4) {
			throw new IllegalArgumentException("Not Enough Arguments Passed to the method");
		}
		
		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		
		AccessLogSummarizer r = new AccessLogSummarizer();
		r.processAccessLog(options);
	
	}
	
	
	
}
