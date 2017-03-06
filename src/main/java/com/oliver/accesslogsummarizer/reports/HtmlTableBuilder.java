package com.oliver.accesslogsummarizer.reports;

import java.text.DecimalFormat;
import java.util.List;

import com.oliver.accesslogsummarizer.beans.Metric;

public class HtmlTableBuilder {

	private static final String TABLE_HEADER = "<table border='1'><thead>";
	private static final String TABLE_FOOTER = "</tbody></table><br /><br />";
	private static final DecimalFormat decimalFormat = new DecimalFormat("0.000");
	private String caption;
	private String headerFields;
	private String body;
	private String footer = TABLE_FOOTER;
	
	public HtmlTableBuilder() {
		
	}
	
	public HtmlTableBuilder caption(String caption) {
			this.caption = "<caption>" + caption + "</caption>";
			return this;
	}
	
	public  HtmlTableBuilder header(String[] headerFields) {
			StringBuilder sb = new StringBuilder();
			sb.append("<tr>");
			for(int i = 0 ; i < headerFields.length ; i++) {
				sb.append("<th>").append(headerFields[i]).append("</th>");
			}
			sb.append("</tr>");
		
			this.headerFields = sb.toString();
			
			return this;
	}
	
	public HtmlTableBuilder body(List<Metric> metrics, int timeFactor, boolean containsTimeParam) {
		StringBuilder sb = new StringBuilder();
		metrics.forEach(metric -> {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(metric.getUrl());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(metric.getCount());
			sb.append("</td>");
			sb.append("<td>");
			if(containsTimeParam) {
				sb.append(decimalFormat.format((double) metric.getAvg()/timeFactor));
				sb.append("</td>");
				sb.append("<td>");
				sb.append(decimalFormat.format((double) metric.get95Percentile()/timeFactor));
				sb.append("</td>");
			} else {
				sb.append("NA");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("NA");
				sb.append("</td>");
			}
			sb.append("</tr>");
		});
		
		this.body = sb.toString();
		
		return this;
	}
	
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(TABLE_HEADER);
		sb.append(this.caption);
		sb.append(this.headerFields);
		sb.append(this.body);
		sb.append(footer);
		
		return sb.toString();
	}
	
	
}
