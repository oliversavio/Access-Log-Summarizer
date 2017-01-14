package com.oliver.accesslogsummarizer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tdunning.math.stats.TDigest;

public class AccessLogSummarizer {

	
	private DecimalFormat decimalFormat = new DecimalFormat("0.000");
	private static final String HEAD = "<! DOCTYPE html><html><title>Access Log Report</title><head></head><body><table border='1'><thead><tr><th>URL</th><th>COUNT</th><th>AVG</th><th>95th %TILE</th></tr></thead><tbody>";
	private static final String TAIL = "</tbody></table></body></html>";
	private static final String OUT_FILE_PATH = "accesslog_report.html";
	private static final int URL_COUNT_THRESHOLD = 100;

	public void processAccessLog(String fileName, int urlIndx, int timeIndx) {
		Map<String, Metric> map = new HashMap<>();
		
		System.out.println("Processing..");
		long s1 = System.currentTimeMillis();
		
		createUrlMap(fileName, urlIndx, timeIndx, map);
		
		List<Metric> filteredMetrics = filterMapValues(map);
		
		displayResults(filteredMetrics);
		
		long e1 = System.currentTimeMillis();
		
		System.out.println("Done!");
		System.out.println("Processing Time(s): " + (e1-s1)/1000d);
	}

	/**
	 * Filters out the URLs while have a count less than the defined Threshold value.
	 * Default Threshold value is 10. 
	 * @param map
	 * @return
	 */
	private List<Metric> filterMapValues(Map<String, Metric> map) {
		List<Metric> filteredMetrics = map.entrySet().stream()
				.filter(mapp -> mapp.getValue().count > URL_COUNT_THRESHOLD)
				.map(mapped -> mapped.getValue())
				.collect(Collectors.toList());
		return filteredMetrics;
	}

	private void createUrlMap(String fileName, int urlIndx, int timeIndx, Map<String, Metric> map) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream
			.filter(str -> !str.startsWith("#"))
			.map(s -> s.split(" "))
			.forEach(arr -> process(map, arr, urlIndx, timeIndx));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayResults(List<Metric> metrics) {

		Comparator<Metric> comparator = Comparator.comparingLong(Metric::getCount);
		Collections.sort(metrics, comparator.reversed());
		
		StringBuilder sb = new StringBuilder();
		sb.append(HEAD);
		
		
		
		metrics.forEach(metric -> {
			sb.append("<tr>");
			sb.append("<td>");sb.append(metric.url);sb.append("</td>");
			sb.append("<td>");sb.append(metric.count);sb.append("</td>");
			sb.append("<td>");sb.append(decimalFormat.format(metric.avg / 1000));sb.append("</td>");
			sb.append("<td>");sb.append(decimalFormat.format(metric.digest.quantile(0.95) / 1000));sb.append("</td>");
			sb.append("</tr>");
		});
		
		sb.append(TAIL);
		
		writeTofile(sb.toString());
	}

	private void writeTofile(String string) {
		
		Path path = Paths.get(OUT_FILE_PATH);
		try(BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(string);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}

	private void process(Map<String, Metric> map, String[] arr, int urlIndx, int timeIndx) {
		if (arr == null) {
			throw new IllegalArgumentException("String split value is null");
		}

		if (urlIndx >= arr.length || timeIndx >= arr.length) {
			return;
			//throw new IllegalArgumentException("URL and Time Index Specified are incorrect");
		}

		String keyUrl = arr[urlIndx];
		long valueTime = Long.parseLong(arr[timeIndx]);
		Metric metric = map.get(keyUrl);
		if (metric == null) {
			metric = new Metric(keyUrl, valueTime);
		} else {
			metric.add(valueTime);
		}
		map.put(keyUrl, metric);
	}

	class Metric {
		String url;
		long count;
		long totaTime;
		double avg;
		TDigest digest;

		Metric(String url, long time) {
			digest = TDigest.createDigest(100.0);
			url = url;
			add(time);
		}

		public long getCount() {
			return this.count;
		}
		
		public void add(long time) {
			count++;
			digest.add(time);
			totaTime += time;
			avg = totaTime / count;
		}

	}

	
	
public static void main(String[] args) {
		
		if(args.length < 3) {
			throw new IllegalArgumentException("Not Enough Arguments Passed to the method");
		}
		
		String fileName = args[0];
		int urlIndex = Integer.parseInt(args[1]);
		int timeIndex = Integer.parseInt(args[2]);
		
		AccessLogSummarizer r = new AccessLogSummarizer();
		r.processAccessLog(fileName, urlIndex, timeIndex);
	
	}
	
	
	
}
