package com.oliver.accesslogsummarizer.beans;

import java.util.Map;

public class ReportContext {

	private Map<String, Metric> metricMap;
	private int timeFactor;
	private boolean containsTimeParam;

	public Map<String, Metric> getMetricMap() {
		return metricMap;
	}

	public void setMetricMap(Map<String, Metric> metricMap) {
		this.metricMap = metricMap;
	}

	public int getTimeFactor() {
		return timeFactor;
	}

	public void setTimeFactor(int timeFactor) {
		this.timeFactor = timeFactor;
	}

	public boolean isContainsTimeParam() {
		return containsTimeParam;
	}

	public void setContainsTimeParam(boolean containsTimeParam) {
		this.containsTimeParam = containsTimeParam;
	}

}
