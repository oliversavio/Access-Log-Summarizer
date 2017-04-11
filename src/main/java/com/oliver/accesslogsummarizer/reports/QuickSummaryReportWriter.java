package com.oliver.accesslogsummarizer.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ReportContext;

public class QuickSummaryReportWriter extends ReportWriter {

	@Override
	public void generateReport(ReportContext context) {
		
		StringBuilder sb = new StringBuilder();
		List<Metric> metrics = context.getMetrics();
		
		// Sort URLs by descending order of count
		Comparator<Metric> comparator = Comparator.comparingLong(Metric::getCount);
		Collections.sort(metrics, comparator.reversed());
		
		int listSize = metrics.size();
		double topTwenty = Math.round(listSize * 0.2);
		
		HtmlTableBuilder table = new HtmlTableBuilder()
				 .caption("Top URL by Count")
				 .header(new String[]{"URL","COUNT","AVG","95th %tile"})	
				 .body(getTop20List(metrics, topTwenty), context.getTimeFactor(), context.isContainsTimeParam());
		
		sb.append(table.toString());
		
		// Sort URLs by descending order of response time
		Comparator<Metric> percentileComparator = Comparator.comparingDouble(Metric::get95Percentile);
		Collections.sort(metrics, percentileComparator.reversed());
		
		HtmlTableBuilder table2 = new HtmlTableBuilder()
				 .caption("Top 95 %tile by Response Time")
				 .header(new String[]{"URL","COUNT","AVG","95th %tile"})	
				 .body(getTop20List(metrics, topTwenty), context.getTimeFactor(), context.isContainsTimeParam());
		
		
		sb.append(table2.toString());
		
		writeToFile(sb.toString(),"QuickSummary.html");
		
		
	}

	private List<Metric> getTop20List(List<Metric> metrics, double topTwenty) {
		List<Metric> top20List = new ArrayList<Metric>((int) topTwenty);
		for(int i = 0 ; i < topTwenty ; i++) {
			top20List.add(metrics.get(i));
		}
		return top20List;
	}

}
