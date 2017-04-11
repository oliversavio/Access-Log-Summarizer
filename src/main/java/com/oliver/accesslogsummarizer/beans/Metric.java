package com.oliver.accesslogsummarizer.beans;

import com.tdunning.math.stats.TDigest;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Metric {
	private String url;
	private long count;
	private double totaTime;
	
	@Getter(AccessLevel.NONE)
	private TDigest digest;
	
	public static Metric MetricWithTime(String url, long time) {
		return new Metric(url, time);
	}
	
	private Metric(String url, long time) {
		digest = TDigest.createDigest(100.0);
		this.url = url;
		addTime(time);
	}

	public Metric(String url) {
		this.url = url;
		add();
	}

	
	public double getAvg() {
		return totaTime / count;
	}

	public void addTime(long time) {
		count++;
		digest.add(time);
		totaTime += time;
	}

	public double get95Percentile() {
		if(this.digest != null)
			return this.digest.quantile(0.95);
		else
			return -1.0;
	}
	
	public void add() {
		count++;
	}

}
