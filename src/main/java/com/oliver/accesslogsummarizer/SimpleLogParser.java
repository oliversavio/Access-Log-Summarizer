package com.oliver.accesslogsummarizer;

import java.util.Arrays;
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

		if (stream == null) {
			throw new IllegalArgumentException("Couldn't process stream");
		}

		Map<String, Metric> map = new HashMap<>();
		stream
		.filter(str -> !str.startsWith("#"))
		.map(s -> s.split(" "))
		.forEach(arr -> process(map, arr, options));

		return map;
	}

	/**
	 * Logs all errors to the log file, will not stop parsing subsequent lines.
	 * 
	 * @param map
	 * @param arr
	 * @param urlIndx
	 * @param timeIndx
	 */
	private void process(Map<String, Metric> map, String[] arr, ParsingOptions options) {
		if (arr == null) {
			logger.debug("String split value is null");
			return;
		}

		if (options.getUrlIndex() >= arr.length || options.getTimeTakenIndex() >= arr.length) {
			logger.debug("URL and Time Index Specified are incorrect for array, skipping record!");
			if(logger.isDebugEnabled()) {
				logger.debug("Skipped record Details:{} ", Arrays.toString(arr));
			}
			return;
		}

		String keyUrl = arr[options.getUrlIndex()];
		long valueTime = 0;

		if (options.isContainsTimeValue()) {
			try {
				valueTime = Long.parseLong(arr[options.getTimeTakenIndex()]);
			} catch (NumberFormatException ex) {
				logger.debug("couldnt parse time value, skipping record.", ex);
				return;
			}
		}

		Metric metric = map.get(keyUrl);

		if (metric == null) {
			if (options.isContainsTimeValue()) {
				metric = Metric.MetricWithTime(keyUrl, valueTime);
			} else {
				metric = new Metric(keyUrl);
			}
		} else {
			if (options.isContainsTimeValue()) {
				metric.addTime(valueTime);
			} else {
				metric.add();
			}
		}
		map.put(keyUrl, metric);
	}

}
