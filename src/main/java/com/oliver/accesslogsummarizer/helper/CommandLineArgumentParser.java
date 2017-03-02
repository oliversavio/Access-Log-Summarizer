package com.oliver.accesslogsummarizer.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.ParsingOptions;

public class CommandLineArgumentParser {

	private static CommandLineArgumentParser instance = null;
	private static final Logger logger = LogManager.getLogger(CommandLineArgumentParser.class);
	private CommandLineArgumentParser() {

	}

	public static CommandLineArgumentParser getInstance() {
		if (instance == null) {
			synchronized (CommandLineArgumentParser.class) {
				if (instance == null) {
					instance = new CommandLineArgumentParser();
				}
			}
		}
		return instance;
	}

	public ParsingOptions parseArguments(String[] args) {
		ParsingOptions options = null;
		String fileName = null;
		int urlIndex = -1, timeIndex = -1, timeFactor = 1;
		if (args == null || args.length < 4) {
			throw new IllegalArgumentException("Program expects at least 2 arguments.");
		}

		for(int i = 0 ; i < args.length ;) {
			String argKey = args[i];
			String argVal;
			
			switch(argKey) {
				case "-f" :
					argVal = args[i+1];
					fileName = argVal;
					logger.debug("File Name: " + argVal);
					i = i + 2;
					break;
				case "-ui" :
					argVal = args[i+1];
					urlIndex = parseIntegerArgument(argVal, "Couldn't parse URL index value");
					logger.debug("URL Index: " + argVal);
					i = i + 2;
					break;
				case "-ti" :
					argVal = args[i+1];
					timeIndex = parseIntegerArgument(argVal, "Couldn't parse Time index value");
					logger.debug("Time Name: " + argVal);
					i = i + 2;
					break;
				case "-m" :
					argVal = args[i];
					timeFactor = 1000000;
					logger.debug("Time factor micro: " + argVal);
					i = i + 1;
					break;
				
				case "-M" :
					argVal = args[i];
					timeFactor = 1000;
					logger.debug("Time factor milli: " + argVal);
					i = i + 1;
					break;	
				
				default:
						logger.debug("Invalid Arguments passed: " + argKey);
			}
			
		}
		
		options = new ParsingOptions(fileName, urlIndex, timeIndex, timeFactor);

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
