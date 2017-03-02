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
import com.oliver.accesslogsummarizer.helper.CommandLineArgumentParser;
import com.oliver.accesslogsummarizer.reports.QuickSummaryReportWriter;
import com.oliver.accesslogsummarizer.reports.ReportProcessor;

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
		
		//ReportProcessor report = new ReportProcessor(new DefaultReportWriter());
		ReportProcessor report = new ReportProcessor(new QuickSummaryReportWriter());
		report.generateReport(metricMap);
		
		long e1 = System.currentTimeMillis();
		
		logger.info("Done!");
		logger.info("Processing Time(s): " + (e1-s1)/1000d);
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
