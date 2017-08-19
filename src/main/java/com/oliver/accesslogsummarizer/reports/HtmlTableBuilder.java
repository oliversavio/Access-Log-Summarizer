package com.oliver.accesslogsummarizer.reports;

import java.util.Map;

import com.oliver.accesslogsummarizer.reports.AccessLogReport.ColumnName;
import com.oliver.accesslogsummarizer.reports.AccessLogReport.ReportValues;

public class HtmlTableBuilder {

	private static final String TABLE_HEADER = "<table border='1'><thead>";
	private static final String TABLE_FOOTER = "</tbody></table><br /><br />";
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
	
	public  HtmlTableBuilder header(Map<String, String> colNames) {
			StringBuilder sb = new StringBuilder();
			sb.append("<tr>");
				sb.append("<th>").append(colNames.get(ColumnName.URL)).append("</th>");
				sb.append("<th>").append(colNames.get(ColumnName.COUNT)).append("</th>");
				sb.append("<th>").append(colNames.get(ColumnName.AVG)).append("</th>");
				sb.append("<th>").append(colNames.get(ColumnName.PERCENTILE)).append("</th>");
			sb.append("</tr>");
		
			this.headerFields = sb.toString();
			
			return this;
	}
	
	public HtmlTableBuilder body(Iterable<ReportValues> rows) {
		StringBuilder sb = new StringBuilder();
		rows.forEach(metric -> {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(metric.getUrl());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(metric.getCount());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(metric.getAverage());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(metric.getPercentile());
			sb.append("</td>");
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
