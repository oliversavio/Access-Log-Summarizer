package com.oliver.accesslogsummarizer.reports;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.Metric;

public abstract class ReportWriter {
	
	private static final Logger logger = LogManager.getLogger(ReportWriter.class);
	protected static final String HEAD = "<! DOCTYPE html><html><title>Access Log Report</title><head></head><body><table border='1'><thead><tr><th>URL</th><th>COUNT</th><th>AVG</th><th>95th %TILE</th></tr></thead><tbody>";
	protected static final String TAIL = "</tbody></table></body></html>";
	protected static final String OUT_FILE_PATH = "accesslog_report.html";
	protected DecimalFormat decimalFormat = new DecimalFormat("0.000");
	
	public abstract void generateReport(Map<String, Metric> metriMap);
	
	
	protected void writeToFile(String result, String fileName) {
		Path path = Paths.get(fileName);
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(result);
		} catch (IOException ex) {
			logger.error("Error while writing file to output: ", ex);
		}
	}
	
	protected String appendHeaderAndFooter(String body) {
		StringBuilder sb = new StringBuilder();
		sb.append(HEAD);
		sb.append(body);
		sb.append(TAIL);
	
		return sb.toString();
	}
	
	
	protected StringBuilder createTable(List<Metric> metrics) {
		StringBuilder sb = new StringBuilder();
		metrics.forEach(metric -> {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(metric.getUrl());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(metric.getCount());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(decimalFormat.format(metric.getAvg() / 1000));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(decimalFormat.format(metric.getDigest().quantile(0.95) / 1000));
			sb.append("</td>");
			sb.append("</tr>");
		});
		
		return sb;
	}
	
}
