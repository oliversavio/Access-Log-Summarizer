package com.oliver.accesslogsummarizer.parser;

import java.util.Map;
import java.util.stream.Stream;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public interface AccessLogParser {

	public Map<String, Metric> parseLog(Stream<String> stream, ParsingOptions options);

	
	
}
