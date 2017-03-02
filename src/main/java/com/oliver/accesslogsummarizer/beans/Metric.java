package com.oliver.accesslogsummarizer.beans;

import com.tdunning.math.stats.TDigest;

public class Metric {
	private String url;
	private long count;
	private double totaTime;
	private double avg;
	private TDigest digest;
	private double timefactor;

	public Metric(String url, long time, double timefactor) {
		digest = TDigest.createDigest(100.0);
		this.url = url;
		this.timefactor = timefactor;
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

	public long getCount() {
		return this.count;
	}

	public String getUrl() {
		return url;
	}

	public double getTotaTime() {
		return totaTime;
	}

	public double getAvg() {
		return avg;
	}

	public TDigest getDigest() {
		return digest;
	}

	public void add(long time) {
		count++;
		double temp = (double) time / this.timefactor;
		digest.add(temp);
		totaTime += temp;
		avg = totaTime / count;
	}

	
	public double get95Percentile() {
		return this.digest.quantile(0.95);
	}
	
	public void add() {
		count++;
	}

}
