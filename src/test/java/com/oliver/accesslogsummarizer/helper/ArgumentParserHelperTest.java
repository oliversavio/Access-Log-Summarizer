package com.oliver.accesslogsummarizer.helper;

import static org.junit.Assert.*;
import org.junit.Test;

import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public class ArgumentParserHelperTest {

	@Test
	public void testParseOptionWithTimeIndex() {

		String[] args = new String[] { "fileName", "0", "1" };

		ParsingOptions options = ArgumentParserHelper.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertEquals(options.getTimeTakenIndex(), 1);
	}

	@Test
	public void testParseOptionWithoutTimeIndex() {

		String[] args = new String[] { "fileName", "0" };

		ParsingOptions options = ArgumentParserHelper.getInstance().parseArguments(args);
		assertEquals(options.getFileName(), "fileName");
		assertEquals(options.getUrlIndex(), 0);
		assertFalse(options.isContainsTimeValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseOptionUrlParseException() {
		String[] args = new String[] { "fileName", "a" };
		ArgumentParserHelper.getInstance().parseArguments(args);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseOptionTooFewArgumentException() {
		String[] args = new String[] { "fileName" };
		ArgumentParserHelper.getInstance().parseArguments(args);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseOptionTimeParseException() {
		String[] args = new String[] { "fileName", "0", "q" };
		ArgumentParserHelper.getInstance().parseArguments(args);
	}

}
