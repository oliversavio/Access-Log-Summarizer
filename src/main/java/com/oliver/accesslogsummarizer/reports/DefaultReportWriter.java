package com.oliver.accesslogsummarizer.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ReportContext;

/**
 * Default Report, displays all URLs with count > 100. Report is sorted by
 * descending order of URL count
 * 
 * @author olivermascarenhas
 *
 */
public class DefaultReportWriter extends ReportWriter {

	@Override
	public Iterable<AccessLogReport> generateReport(ReportContext context) {

		if(context.getMetrics() == null || context.getMetrics().isEmpty()) {
			throw new IllegalStateException("Metrics List isn't set, cannot proceed with Report Generation");
		}
		
		List<Metric> metrics = context.getMetrics()
				.stream()
				.filter(metric -> metric.getCount() > context.getCountFilter())
				.collect(Collectors.toList());

		// Sort URLs by descending order of count
		Comparator<Metric> comparator = Comparator.comparingLong(Metric::getCount);
		Collections.sort(metrics, comparator.reversed());

		AccessLogReport report = new AccessLogReport("Default Report");
		metrics.forEach(metric -> report.addRow(metric, context));
		
		List<AccessLogReport> result = new ArrayList<>(1);
		result.add(report);
		return result;
	}

}
