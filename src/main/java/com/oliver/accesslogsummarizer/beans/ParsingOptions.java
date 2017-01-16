package com.oliver.accesslogsummarizer.beans;

public class ParsingOptions {

	private int urlIndex;
	private int timeTakenIndex;

	public ParsingOptions(int urlIndex, int timeTakenIndex) {
		super();
		this.urlIndex = urlIndex;
		this.timeTakenIndex = timeTakenIndex;
	}

	public int getUrlIndex() {
		return urlIndex;
	}

	public int getTimeTakenIndex() {
		return timeTakenIndex;
	}

}
