package com.oliver.accesslogsummarizer.beans;

import com.tdunning.math.stats.TDigest;

public class Metric {
	private String url;
	private long count;
	private long totaTime;
	private double avg;
	private TDigest digest;

	public Metric(String url, long time) {
		digest = TDigest.createDigest(100.0);
		this.url = url;
		add(time);
	}

	public long getCount() {
		return this.count;
	}

	public String getUrl() {
		return url;
	}

	public long getTotaTime() {
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
		digest.add(time);
		totaTime += time;
		avg = totaTime / count;
	}

}
