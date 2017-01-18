package com.oliver.accesslogsummarizer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public class SimpeLogParserTest {

	private Map<String, Metric> map;
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
		map = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 9));
		assertEquals(5, map.size());
	}
	
	@Test
	public void testIncorrectParseOptions() {
		AccessLogParser parser =  new SimpleLogParser();
		map = parser.parseLog(Arrays.stream(log), new ParsingOptions(null,6, 7));
		assertEquals(0, map.size());
	}
	
	@Test
	public void testIndexOutOfRange() {
		AccessLogParser parser =  new SimpleLogParser();
		map = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 10));
		assertEquals(0, map.size());
	}
	
	@Test
	public void testParserAvg() {
		AccessLogParser parser =  new SimpleLogParser();
		map = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 9));
		assertEquals(3985.0, map.get("/shuttle/countdown/").getAvg(), 0.01);
	}
	
	@Test
	public void testParserTotal() {
		AccessLogParser parser =  new SimpleLogParser();
		map = parser.parseLog(Arrays.stream(log), new ParsingOptions(null, 6, 9));
		assertEquals(4179, map.get("/shuttle/missions/sts-73/sts-73-patch-small.gif").getTotaTime(), 0.01);
	}
	
	
}
