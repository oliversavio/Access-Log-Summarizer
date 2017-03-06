package com.oliver.accesslogsummarizer.helper;

import static org.junit.Assert.*;
import org.junit.Test;

import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public class ArgumentParserHelperTest {

	@Test
	public void testParseOptionWithTimeIndex() {

		String[] args = new String[] { "-f","fileName", "-ui", "1", "-ti", "2" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals("fileName", options.getFileName());
		assertEquals(0, options.getUrlIndex());
		assertEquals(1, options.getTimeTakenIndex());
	}

	@Test
	public void testParseOptionWithoutTimeIndex() {

		String[] args = new String[] { "-f","fileName", "-ui", "1" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals("fileName", options.getFileName());
		assertEquals(0, options.getUrlIndex());
		assertFalse(options.isContainsTimeValue());
	}
	
	@Test
	public void testRandomOrder() {

		String[] args = new String[] { "-ui", "1", "-f","fileName" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals("fileName", options.getFileName());
		assertEquals(0, options.getUrlIndex());
		assertFalse(options.isContainsTimeValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseOptionUrlParseException() {
		String[] args = new String[] { "fileName", "a" };
		CommandLineArgumentParser.getInstance().parseArguments(args);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseOptionTooFewArgumentException() {
		String[] args = new String[] { "fileName" };
		CommandLineArgumentParser.getInstance().parseArguments(args);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseOptionTimeParseException() {
		String[] args = new String[] { "fileName", "0", "q" };
		CommandLineArgumentParser.getInstance().parseArguments(args);
	}

	@Test
	public void testTimeFactorMilli() {
		String[] args = new String[] { "-ui", "1", "-f","fileName","-M" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1000);
	}
	
	@Test
	public void testTimeFactorMicro() {
		String[] args = new String[] { "-m","-ui", "1", "-f","fileName" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1000000);
	}
	
	@Test
	public void testTimeFactorDefault() {
		String[] args = new String[] { "-ui", "1", "-f","fileName"};

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals("fileName", options.getFileName());
		assertEquals(0, options.getUrlIndex());
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1);
	}
	
	@Test
	public void testTimeFactorMiddle() {
		String[] args = new String[] { "-ui", "1","-M", "-f","fileName"};

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1000);
	}
	
	
}
