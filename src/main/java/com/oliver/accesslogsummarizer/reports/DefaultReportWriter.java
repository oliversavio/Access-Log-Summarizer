package com.oliver.accesslogsummarizer.reports;

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
	public void generateReport(ReportContext context) {

		List<Metric> metrics = context.getMetrics()
				.stream()
				.filter(metric -> metric.getCount() > 100)
				.collect(Collectors.toList());

		// Sort URLs by descending order of count
		Comparator<Metric> comparator = Comparator.comparingLong(Metric::getCount);
		Collections.sort(metrics, comparator.reversed());

		HtmlTableBuilder table = new HtmlTableBuilder()
								 .caption("Default Report")
								 .header(new String[]{"URL","COUNT","AVG","95th %tile"})	
								 .body(metrics, context.getTimeFactor(), context.isContainsTimeParam());
		
		String report = table.toString();
		writeToFile(report,"DefaultReport1.html");
	}

}
