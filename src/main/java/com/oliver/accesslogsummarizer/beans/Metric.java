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
	
	public Metric(String url, long time, double timefactor) {
		digest = TDigest.createDigest(100.0);
		this.url = url;
		add(time);
	}

	public Metric(String url, long time) {
		digest = TDigest.createDigest(100.0);
		this.url = url;
		add(time);
	}

	public Metric(String url) {
		this.url = url;
		add();
	}

	public double getAvg() {
		return totaTime / count;
	}

	public void add(long time) {
		count++;
		digest.add(time);
		totaTime += time;
	}

	public double get95Percentile() {
		return this.digest.quantile(0.95);
	}
	
	public void add() {
		count++;
	}

}
