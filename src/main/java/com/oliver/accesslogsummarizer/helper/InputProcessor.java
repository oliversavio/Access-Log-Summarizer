package com.oliver.accesslogsummarizer.helper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.parser.AccessLogParser;
import com.oliver.accesslogsummarizer.parser.SimpleLogParser;
import com.oliver.accesslogsummarizer.reader.FileStream;
import com.oliver.accesslogsummarizer.reader.InputStream;

public class InputProcessor {

	private static final Logger logger = LogManager.getLogger(InputProcessor.class);
	private static InputProcessor INSTANCE = null;
	private AccessLogParser parser = null;
	private InputStream<String> input = null;

	private InputProcessor() {
		parser = new SimpleLogParser();
		input = new FileStream();
	}

	public static InputProcessor getInstance() {
		if (INSTANCE == null) {
			synchronized (InputProcessor.class) {
				if (INSTANCE == null) {
					INSTANCE = new InputProcessor();
				}
			}
		}

		return INSTANCE;
	}

	public Optional<List<Metric>> processAccessLog(ParsingOptions options) {
		Stream<String> inputStream =  input.getStream(options.getFileName());
		List<Metric> metrics = parser.parseLog(inputStream, options);
		
		closeInputStream(inputStream);
		
		return Optional.ofNullable(metrics);
	}

	private void closeInputStream(Stream<String> inputStream) {
		try {
			inputStream.close();
		} catch(Exception ex) {
			logger.error("Error while closing Stream", ex);
		}
	}

}
