package com.oliver.accesslogsummarizer;

import java.util.Map;

import com.oliver.accesslogsummarizer.beans.Metric;

public interface ResultProcessor {
	
	
	public void processResults(Map<String, Metric> metriMap);

}
