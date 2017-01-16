package com.oliver.accesslogsummarizer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.Metric;

public class DefaultHtmlOutputResultProcessor implements ResultProcessor {

	private static final Logger logger = LogManager.getLogger(DefaultHtmlOutputResultProcessor.class);
	private static final String HEAD = "<! DOCTYPE html><html><title>Access Log Report</title><head></head><body><table border='1'><thead><tr><th>URL</th><th>COUNT</th><th>AVG</th><th>95th %TILE</th></tr></thead><tbody>";
	private static final String TAIL = "</tbody></table></body></html>";
	private static final String OUT_FILE_PATH = "accesslog_report.html";
	private static final int URL_COUNT_THRESHOLD = 100;
	private DecimalFormat decimalFormat = new DecimalFormat("0.000");

	@Override
	public void processResults(Map<String, Metric> metriMap) {

		List<Metric> filetredMetrics = filterMapValues(metriMap);

		//Sort URLs as descending order of count
		Comparator<Metric> comparator = Comparator.comparingLong(Metric::getCount);
		Collections.sort(filetredMetrics, comparator.reversed());

		writeToFile(filetredMetrics);

	}

	/**
	 * Filters out the URLs while have a count less than the defined Threshold
	 * value. Default Threshold value is 10.
	 * 
	 * @param map
	 * @return
	 */
	private List<Metric> filterMapValues(Map<String, Metric> map) {
		List<Metric> filteredMetrics = map.entrySet().stream()
				.filter(mapp -> mapp.getValue().getCount() > URL_COUNT_THRESHOLD)
				.map(mapped -> mapped.getValue())
				.collect(Collectors.toList());
		return filteredMetrics;
	}

	/**
	 * Sorts the Urls in descending order of count and renders display to HTML
	 * 
	 * @param metrics
	 */
	private void writeToFile(List<Metric> metrics) {

		StringBuilder sb = new StringBuilder();
		sb.append(HEAD);

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

		sb.append(TAIL);

		Path path = Paths.get(OUT_FILE_PATH);
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(sb.toString());
		} catch (IOException ex) {
			logger.error("Error while writing file to output: ", ex);
		}
	}

}
