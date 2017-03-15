package com.oliver.accesslogsummarizer.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ParsingOptions {

	public enum ReportType {DEFAULT, QUICK_SUMMARY}
	private String fileName;
	private int urlIndex;
	private int timeTakenIndex;
	private boolean containsTimeValue = false;
	private int timeFactor;
	private ReportType reportType;
	
	public ParsingOptions(String url, int urlIndex, int timeTakenIndex, int timeFactor, ReportType reportType) {
		super();
		this.fileName = url;
		this.urlIndex = urlIndex;
		this.timeTakenIndex = timeTakenIndex;
		if (timeTakenIndex >= 0) {
			this.containsTimeValue = true;
		}
		this.timeFactor = timeFactor;
		this.reportType = reportType;
	}
	
	
}
