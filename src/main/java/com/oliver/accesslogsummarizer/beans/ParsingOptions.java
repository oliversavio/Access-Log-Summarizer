package com.oliver.accesslogsummarizer.beans;

public class ParsingOptions {

	public enum ReportType {DEFAULT, QUICK_SUMMARY}
	private String fileName;
	private int urlIndex;
	private int timeTakenIndex;
	private boolean containsTimeValue = false;
	private int timeFactor;
	private ReportType reportType;
	
	public ParsingOptions() {
		
	}
	
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

	public int getUrlIndex() {
		return urlIndex;
	}

	public int getTimeTakenIndex() {
		return timeTakenIndex;
	}

	public String getFileName() {
		return fileName;
	}

	public boolean isContainsTimeValue() {
		return containsTimeValue;
	}

	public int getTimeFactor() {
		return timeFactor;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUrlIndex(int urlIndex) {
		this.urlIndex = urlIndex;
	}

	public void setTimeTakenIndex(int timeTakenIndex) {
		this.timeTakenIndex = timeTakenIndex;
	}

	public void setContainsTimeValue(boolean containsTimeValue) {
		this.containsTimeValue = containsTimeValue;
	}

	public void setTimeFactor(int timeFactor) {
		this.timeFactor = timeFactor;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
	
	
	
}
