package com.oliver.accesslogsummarizer.beans;

import java.util.Map;

import lombok.Data;


@Data
public class ReportContext {

	private Map<String, Metric> metricMap;
	private int timeFactor;
	private boolean containsTimeParam;

}
