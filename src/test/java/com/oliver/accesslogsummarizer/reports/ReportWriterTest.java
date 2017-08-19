package com.oliver.accesslogsummarizer.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;
import com.oliver.accesslogsummarizer.beans.ReportContext;
import com.oliver.accesslogsummarizer.parser.AccessLogParser;
import com.oliver.accesslogsummarizer.parser.SimpleLogParser;
import com.oliver.accesslogsummarizer.reports.AccessLogReport.ReportValues;

public class ReportWriterTest {

	private static List<Metric> metricList;
	private static ReportContext context;
	private static String[] log = new String[] {
			"#Some"
			,"#Header"
			,"199.72.81.55 - - [01/Jul/1995:00:00:01 -0400] \"GET /history/apollo/ HTTP/1.0\" 200 6245"
			,"unicomp6.unicomp.net - - [01/Jul/1995:00:00:06 -0400] \"GET /shuttle/countdown/ HTTP/1.0\" 200 3985"
			,"199.120.110.21 - - [01/Jul/1995:00:00:09 -0400] \"GET /shuttle/missions/sts-73/mission-sts-73.html HTTP/1.0\" 200 4085"
			,"burger.letters.com - - [01/Jul/1995:00:00:11 -0400] \"GET /shuttle/countdown/liftoff.html HTTP/1.0\" 304 0"
			,"199.120.110.21 - - [01/Jul/1995:00:00:11 -0400] \"GET /shuttle/missions/sts-73/sts-73-patch-small.gif HTTP/1.0\" 200 4179"

	};
	
	@BeforeClass
	public static void setup() {
		AccessLogParser parser =  new SimpleLogParser();
		ParsingOptions options = new ParsingOptions(null, 6, 9, 1, ReportType.DEFAULT);
		metricList = parser.parseLog(Arrays.stream(log), options);
		context = new ReportContext(options);
		context.setMetrics(metricList);
		context.setCountFilter(0);
	}
	
	@Test
	public void defaultReportTest() {
		context.setReportType(ReportType.DEFAULT);
		ReportWriter writer = new DefaultReportWriter();
		Iterable<AccessLogReport> result = writer.generateReport(context);
		assertNotNull("ReportWriter result is Null", result);
		AccessLogReport report = result.iterator().next();
		assertNotNull(report);
		assertEquals(5, report.getRows().size());
	}

	@Test
	public void defaultReportCountTest() {
		context.setReportType(ReportType.DEFAULT);
		ReportWriter writer = new DefaultReportWriter();
		Iterable<AccessLogReport> result = writer.generateReport(context);
		AccessLogReport report = result.iterator().next();
		
		assertEquals("1", report.getRows().get(0).getCount());
		assertEquals("1", report.getRows().get(1).getCount());
		assertEquals("1", report.getRows().get(2).getCount());
	}
	
	@Test
	public void defaultReportPercentileTest() {
		context.setReportType(ReportType.DEFAULT);
		context.setTimeFactor(1);
		ReportWriter writer = new DefaultReportWriter();
		Iterable<AccessLogReport> result = writer.generateReport(context);
		AccessLogReport report = result.iterator().next();
		
		
		assertEquals("6245.000", getPercentile(report.getRows(), "/history/apollo/"));
		assertEquals("3985.000", getPercentile(report.getRows(), "/shuttle/countdown/"));
		assertEquals("4085.000", getPercentile(report.getRows(), "/shuttle/missions/sts-73/mission-sts-73.html"));
	}
	
	
	@Test
	public void defaultReportTimeFactorTest() {
		context.setReportType(ReportType.DEFAULT);
		context.setTimeFactor(1000);
		ReportWriter writer = new DefaultReportWriter();
		Iterable<AccessLogReport> result = writer.generateReport(context);
		AccessLogReport report = result.iterator().next();
		
		assertEquals("6.245", getPercentile(report.getRows(), "/history/apollo/"));
		assertEquals("3.985", getPercentile(report.getRows(), "/shuttle/countdown/"));
		assertEquals("4.085", getPercentile(report.getRows(), "/shuttle/missions/sts-73/mission-sts-73.html"));
	}
	
	
	@Test
	public void quickSummaryReportTest() {
		context.setReportType(ReportType.QUICK_SUMMARY);
		ReportWriter writer = new QuickSummaryReportWriter();
		Iterable<AccessLogReport> result = writer.generateReport(context);
		Iterator<AccessLogReport> it = result.iterator();
		assertNotNull(it.next());
		assertNotNull(it.next());
		Assert.assertFalse(it.hasNext());
	}
	
	
	@Test
	public void quickSummaryReportPercentileTest() {
		context.setReportType(ReportType.QUICK_SUMMARY);
		context.setTimeFactor(1);
		ReportWriter writer = new QuickSummaryReportWriter();
		Iterable<AccessLogReport> result = writer.generateReport(context);
		Iterator<AccessLogReport> it = result.iterator();
		AccessLogReport countReport = it.next();
		AccessLogReport percentileReport = it.next();
		
		
		assertEquals("6245.000", percentileReport.getRows().get(0).getPercentile());
		Assert.assertTrue(percentileReport.getRows().size() == 1);
		Assert.assertTrue(countReport.getRows().size() == 1);
	}
	
	private static String getPercentile(List<ReportValues> values, String url) {
		return values.stream()
		.filter(value -> value.getUrl().equals(url))
		.findFirst()
		.get()
		.getPercentile();
	}
	
	
}
