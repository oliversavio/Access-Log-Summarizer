package com.oliver.accesslogsummarizer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public class SimpleLogParser implements AccessLogParser {

	private static Logger logger = LogManager.getLogger(SimpleLogParser.class);
	
	@Override
	public Map<String, Metric> parseLog(Stream<String> stream, ParsingOptions options) {
		
		if(stream == null) {
			throw new IllegalArgumentException("Couldn't process stream");
		}
		
		Map<String, Metric> map = new HashMap<>();
		stream
		.filter(str -> !str.startsWith("#"))
		.map(s -> s.split(" "))
		.forEach(arr -> process(map, arr, options.getUrlIndex(), options.getTimeTakenIndex()));
		
		return map;
	}

	/**
	 * Logs all errors to the log file, will not stop parsing subsequent lines.
	 * @param map
	 * @param arr
	 * @param urlIndx
	 * @param timeIndx
	 */
	private void process(Map<String, Metric> map, String[] arr, int urlIndx, int timeIndx) {
		if (arr == null) {
			logger.error("String split value is null");
			return;
		}

		if (urlIndx >= arr.length || timeIndx >= arr.length) {
			logger.error("URL and Time Index Specified are incorrect for array:  " + arr);
			return;
		}

		String keyUrl = arr[urlIndx];
		long valueTime = 0;
		try{
			valueTime = Long.parseLong(arr[timeIndx]);
		} catch(NumberFormatException ex) {
			logger.error("couldnt parse time value, skipping record.", ex);
			return;
		}
		
		
		
		Metric metric = map.get(keyUrl);
		if (metric == null) {
			metric = new Metric(keyUrl, valueTime);
		} else {
			metric.add(valueTime);
		}
		map.put(keyUrl, metric);
	}
	
	
	
}
