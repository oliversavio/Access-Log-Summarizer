package com.oliver.accesslogsummarizer.reports;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ReportContext;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is the canonical representation of {@link Metric} values which can
 * be used for display
 * 
 * @author olivermascarenhas
 *
 */
@Getter
public class AccessLogReport {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000");
	private String caption;
	private List<ReportValues> rows;
	@Setter
	private Map<String, String> columnHeaderMap;

	public AccessLogReport(String caption) {
		this.caption = caption;
		this.rows = new ArrayList<>();
		// Default Column Header, use the setter to override these values.
		columnHeaderMap = new HashMap<>(4);
		columnHeaderMap.put(ColumnName.URL, "Url");
		columnHeaderMap.put(ColumnName.AVG, "Average");
		columnHeaderMap.put(ColumnName.COUNT, "Count");
		columnHeaderMap.put(ColumnName.PERCENTILE, "95th %Tile");

	}

	public void addRow(Metric metric, ReportContext context) {
		ReportValues values = new ReportValues();
		values.url = metric.getUrl();
		values.count = Long.toString(metric.getCount());
		if (context.isContainsTimeParam()) {
			values.average = DECIMAL_FORMAT.format((double) metric.getAvg() / context.getTimeFactor());
			values.percentile = DECIMAL_FORMAT.format((double) metric.get95Percentile() / context.getTimeFactor());
		}
		rows.add(values);
	}

	@Getter
	public class ReportValues {
		private String url = "N/A";
		private String count = "N/A";
		private String average = "N/A";
		private String percentile = "N/A";
	}

	static class ColumnName {
		public static String URL = "URL";
		public static String AVG = "AVG";
		public static String COUNT = "COUNT";
		public static String PERCENTILE = "PERCENTILE";
	}

}
