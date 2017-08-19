package com.oliver.accesslogsummarizer.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ReportContext;
/**
 * Quick Summary reports displays the Top 20 URLs by Count and Response Time.
 * Following the 80-20 rule, this should server as a good start to improve overall performance.
 * 
 * @author olivermascarenhas
 *
 */
public class QuickSummaryReportWriter extends ReportWriter {

	@Override
	public Iterable<AccessLogReport> generateReport(ReportContext context) {
		
		List<Metric> metrics = context.getMetrics();
		
		if(metrics == null || metrics.isEmpty()) {
			throw new IllegalStateException("Metrics List isn't set, cannot proceed with Report Generation");
		}
		
		// Sort URLs by descending order of count
		Comparator<Metric> comparator = Comparator.comparingLong(Metric::getCount);
		Collections.sort(metrics, comparator.reversed());
		
		int listSize = metrics.size();
		double topTwenty = Math.round(listSize * 0.2);
		
		AccessLogReport countReport = new AccessLogReport("Top URL by Count");
		
		getTop20List(metrics, topTwenty)
		.forEach(metric -> {
			countReport.addRow(metric, context);
		});
		
		// Sort URLs by descending order of response time
		Comparator<Metric> percentileComparator = Comparator.comparingDouble(Metric::get95Percentile);
		Collections.sort(metrics, percentileComparator.reversed());
		
		AccessLogReport responseTimeReport = new AccessLogReport("Top 95 %tile by Response Time");
		getTop20List(metrics, topTwenty)
		.forEach(metric -> {
			responseTimeReport.addRow(metric, context);
		});
		
		List<AccessLogReport> reports = new ArrayList<>(2); 
		reports.add(countReport);
		reports.add(responseTimeReport);
		
		return reports;
	}

	private List<Metric> getTop20List(List<Metric> metrics, double topTwenty) {
		List<Metric> top20List = new ArrayList<Metric>((int) topTwenty);
		for(int i = 0 ; i < topTwenty ; i++) {
			top20List.add(metrics.get(i));
		}
		return top20List;
	}

}
