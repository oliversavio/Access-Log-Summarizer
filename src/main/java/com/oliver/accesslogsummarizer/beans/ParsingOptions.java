package com.oliver.accesslogsummarizer.beans;

public class ParsingOptions {

	private String fileName;
	private int urlIndex;
	private int timeTakenIndex;
	private boolean containsTimeValue = false;

	public ParsingOptions(String url, int urlIndex, int timeTakenIndex) {
		super();
		this.fileName = url;
		this.urlIndex = urlIndex;
		this.timeTakenIndex = timeTakenIndex;
		if (timeTakenIndex >= 0) {
			this.containsTimeValue = true;
		}
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

}
