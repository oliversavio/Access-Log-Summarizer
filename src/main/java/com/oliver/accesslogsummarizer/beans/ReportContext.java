package com.oliver.accesslogsummarizer.beans;

import java.util.List;

import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;
import com.oliver.accesslogsummarizer.reports.ReportWriter;

import lombok.Data;


@Data
public class ReportContext {

	private List<Metric> metrics;
	private int timeFactor;
	private boolean containsTimeParam;
	private ReportType reportType;
	private ReportWriter reportWriter;
	private int countFilter;

	public ReportContext(ParsingOptions options) {
		this.timeFactor = options.getTimeFactor();
		this.containsTimeParam = options.isContainsTimeValue();
		this.reportType = options.getReportType();
	}

}
