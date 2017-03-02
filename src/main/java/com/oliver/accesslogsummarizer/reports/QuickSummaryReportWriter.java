package com.oliver.accesslogsummarizer.reports;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.oliver.accesslogsummarizer.beans.Metric;

public class QuickSummaryReportWriter extends ReportWriter {

	@Override
	public void generateReport(Map<String, Metric> metriMap) {
		
		List<Metric> metrics = metriMap.entrySet().stream()
				.map(mMap -> mMap.getValue())
				.collect(Collectors.toList());
		
		
		
	}

}
