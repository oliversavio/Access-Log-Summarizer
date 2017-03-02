package com.oliver.accesslogsummarizer.beans;

public class ParsingOptions {

	private String fileName;
	private int urlIndex;
	private int timeTakenIndex;
	private boolean containsTimeValue = false;
	private int timeFactor;

	public ParsingOptions(String url, int urlIndex, int timeTakenIndex, int timeFactor) {
		super();
		this.fileName = url;
		this.urlIndex = urlIndex;
		this.timeTakenIndex = timeTakenIndex;
		if (timeTakenIndex >= 0) {
			this.containsTimeValue = true;
		}
		this.timeFactor = timeFactor;
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
	
}
