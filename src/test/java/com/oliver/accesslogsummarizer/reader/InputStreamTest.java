package com.oliver.accesslogsummarizer.reader;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import org.junit.Test;


public class InputStreamTest {

	private InputStream<String> input = new FileStream();
	
	@Test
	public void testInputStream() {
		Stream<String> stream = input.getStream("src/test/resources/test_access_log.log");
		assertNotNull(stream);
		assertEquals(13, stream.count());
	}
	
	
}
