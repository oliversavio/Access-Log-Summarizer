package com.oliver.accesslogsummarizer.helper;

import static org.junit.Assert.*;
import org.junit.Test;

import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public class ArgumentParserHelperTest {

	@Test
	public void testParseOptionWithTimeIndex() {

		String[] args = new String[] { "-f","fileName", "-ui", "0", "-ti", "1" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertEquals(options.getTimeTakenIndex(), 1);
	}

	@Test
	public void testParseOptionWithoutTimeIndex() {

		String[] args = new String[] { "-f","fileName", "-ui", "0" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
	}
	
	@Test
	public void testRandomOrder() {

		String[] args = new String[] { "-ui", "0", "-f","fileName" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
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
		String[] args = new String[] { "-ui", "0", "-f","fileName","-M" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1000);
	}
	
	@Test
	public void testTimeFactorMicro() {
		String[] args = new String[] { "-m","-ui", "0", "-f","fileName" };

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1000000);
	}
	
	@Test
	public void testTimeFactorDefault() {
		String[] args = new String[] { "-ui", "0", "-f","fileName"};

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1);
	}
	
	@Test
	public void testTimeFactorMiddle() {
		String[] args = new String[] { "-ui", "0","-M", "-f","fileName"};

		ParsingOptions options = CommandLineArgumentParser.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
		assertEquals(options.getTimeFactor(), 1000);
	}
	
	
}
