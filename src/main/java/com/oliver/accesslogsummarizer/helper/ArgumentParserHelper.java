package com.oliver.accesslogsummarizer.helper;

import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public class ArgumentParserHelper {

	private static ArgumentParserHelper instance = null;

	private ArgumentParserHelper() {

	}

	public static ArgumentParserHelper getInstance() {
		if (instance == null) {
			synchronized (ArgumentParserHelper.class) {
				if (instance == null) {
					instance = new ArgumentParserHelper();
				}
			}
		}
		return instance;
	}

	public ParsingOptions parseArguments(String[] args) {
		ParsingOptions options = null;
		String fileName = null;
		int urlIndex = -1, timeIndex = -1;
		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("Program expects at least 2 arguments.");
		}

		if (args.length >= 2) {
			fileName = args[0];
			urlIndex = parseIntegerArgument(args[1], "Couldn't parse URL index value");
		}

		if (args.length >= 3) {
			timeIndex = parseIntegerArgument(args[2], "Couldn't parse Time index value");
		}

		options = new ParsingOptions(fileName, urlIndex, timeIndex);

		return options;
	}

	private int parseIntegerArgument(String arg, String exceptionString) {
		try {
			return Integer.parseInt(arg);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(exceptionString, ex);
		}
	}

}
