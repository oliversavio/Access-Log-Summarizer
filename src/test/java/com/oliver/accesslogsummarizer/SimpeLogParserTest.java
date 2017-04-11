package com.oliver.accesslogsummarizer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;
import com.oliver.accesslogsummarizer.parser.AccessLogParser;
import com.oliver.accesslogsummarizer.parser.SimpleLogParser;

public class SimpeLogParserTest {

	private List<Metric> lst;
	private String[] log = new String[] {
			"#Some"
			,"#Header"
			,"199.72.81.55 - - [01/Jul/1995:00:00:01 -0400] \"GET /history/apollo/ HTTP/1.0\" 200 6245"
			,"unicomp6.unicomp.net - - [01/Jul/1995:00:00:06 -0400] \"GET /shuttle/countdown/ HTTP/1.0\" 200 3985"
			,"199.120.110.21 - - [01/Jul/1995:00:00:09 -0400] \"GET /shuttle/missions/sts-73/mission-sts-73.html HTTP/1.0\" 200 4085"
			,"burger.letters.com - - [01/Jul/1995:00:00:11 -0400] \"GET /shuttle/countdown/liftoff.html HTTP/1.0\" 304 0"
			,"199.120.110.21 - - [01/Jul/1995:00:00:11 -0400] \"GET /shuttle/missions/sts-73/sts-73-patch-small.gif HTTP/1.0\" 200 4179"

	};
	
	@Test
	public void testParser() {
		AccessLogParser parser =  new SimpleLogParser();
		lst = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 9, 1, ReportType.DEFAULT));
		assertEquals(5, lst.size());
	}
	
	@Test
	public void testIncorrectTimeIndexParseOptions() {
		AccessLogParser parser =  new SimpleLogParser();
		lst = parser.parseLog(Arrays.stream(log), new ParsingOptions(null,6, 7, 1, ReportType.DEFAULT));
		assertEquals(0, lst.size());
	}
	
	@Test
	public void testIndexOutOfRange() {
		AccessLogParser parser =  new SimpleLogParser();
		lst = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 10, 1, ReportType.DEFAULT));
		assertEquals(0, lst.size());
	}
	
	@Test
	public void testParserAvg() {
		AccessLogParser parser =  new SimpleLogParser();
		lst = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 9, 1, ReportType.DEFAULT));
		
		assertEquals(3985.0, getMetricObj("/shuttle/countdown/").getAvg(), 0.01);
	}
	
	@Test
	public void testParserTotal() {
		AccessLogParser parser =  new SimpleLogParser();
		lst = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 9, 1, ReportType.DEFAULT));
		assertEquals(4179, getMetricObj("/shuttle/missions/sts-73/sts-73-patch-small.gif").getTotaTime(), 0.01);
	}
	
	@Test
	public void testParserAvgMilli() {
		AccessLogParser parser =  new SimpleLogParser();
		lst = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 9, 1000, ReportType.DEFAULT));
		assertEquals(3985, getMetricObj("/shuttle/countdown/").getAvg(), 0.01);
	}

	private Metric getMetricObj(String url) {
		Metric met = null;
		for(Metric m : lst) {
			if(m.getUrl().equals(url)) {
				met = m;
				break;
			}
		}
		return met;
	}
	
	
	
}
