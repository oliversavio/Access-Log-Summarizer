package com.oliver.accesslogsummarizer.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.oliver.accesslogsummarizer.beans.Metric;

public class QuickSummaryReportWriter extends ReportWriter {

	@Override
	public void generateReport(Map<String, Metric> metriMap) {
		
		StringBuilder sb = new StringBuilder();
		
		List<Metric> metrics = metriMap.entrySet().stream()
				.filter(mapItem -> mapItem.getValue().getCount() > 100)
				.map(mapItem -> mapItem.getValue())
				.collect(Collectors.toList());
		
		// Sort URLs by descending order of count
		Comparator<Metric> comparator = Comparator.comparingLong(Metric::getCount);
		Collections.sort(metrics, comparator.reversed());
		
		int listSize = metrics.size();
		double topTwenty = Math.round(listSize * 0.2);
		
		HtmlTableBuilder table = new HtmlTableBuilder()
				 .caption("Top URL by Count")
				 .header(new String[]{"URL","COUNT","AVG","95th %tile"})	
				 .body(getTop20List(metrics, topTwenty));
		
		sb.append(table.toString());
		
		// Sort URLs by descending order of response time
		Comparator<Metric> percentileComparator = Comparator.comparingDouble(Metric::get95Percentile);
		Collections.sort(metrics, percentileComparator.reversed());
		
		HtmlTableBuilder table2 = new HtmlTableBuilder()
				 .caption("Top 95 %tile by Response Time")
				 .header(new String[]{"URL","COUNT","AVG","95th %tile"})	
				 .body(getTop20List(metrics, topTwenty));
		
		
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
