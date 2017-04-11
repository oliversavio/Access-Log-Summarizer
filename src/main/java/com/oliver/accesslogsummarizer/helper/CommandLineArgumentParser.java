package com.oliver.accesslogsummarizer.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.beans.ParsingOptions.ReportType;

public class CommandLineArgumentParser {

	private static final Logger logger = LogManager.getLogger(CommandLineArgumentParser.class);

	private CommandLineArgumentParser() {

	}

	public static ParsingOptions ParseArguments(String[] args) {
		ParsingOptions options = new ParsingOptions();
		options.setTimeFactor(1);
		options.setReportType(ReportType.DEFAULT);

		if (args == null || args.length < 4) {
			throw new IllegalArgumentException("Program expects at least 2 arguments.");
		}

		for (int i = 0; i < args.length;) {
			String argKey = args[i];

			switch (argKey) {
			case "-f":
				options.setFileName(args[i + 1]);
				logger.debug("File Name: " + args[i + 1]);
				i = i + 2;
				break;

			case "-ui":
				options.setUrlIndex(parseIntegerArgument(args[i + 1], "Couldn't parse URL index value") - 1);
				logger.debug("URL Index: " + args[i + 1]);
				i = i + 2;
				break;

			case "-ti":
				options.setTimeTakenIndex(parseIntegerArgument(args[i + 1], "Couldn't parse Time index value") - 1);
				options.setContainsTimeValue(true);
				logger.debug("Time Name: " + args[i + 1]);
				i = i + 2;
				break;

			case "-m":
				options.setTimeFactor(1000_000);
				logger.debug("Time factor micro: " + 1000_000);
				i = i + 1;
				break;

			case "-M":
				options.setTimeFactor(1000);
				logger.debug("Time factor milli: " + 1000);
				i = i + 1;
				break;

			case "-Q":
				options.setReportType(ReportType.QUICK_SUMMARY);
				logger.debug("ReportType: " + ReportType.QUICK_SUMMARY);
				i = i + 1;
				break;

			case "-D":
				options.setReportType(ReportType.DEFAULT);
				logger.debug("ReportType: " + ReportType.DEFAULT);
				i = i + 1;
				break;

			default:
				logger.debug("Invalid Arguments passed: " + argKey);
			}

		}

		return options;
	}

	private static int parseIntegerArgument(String arg, String exceptionString) {
		try {
			return Integer.parseInt(arg);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(exceptionString, ex);
		}
	}

}
