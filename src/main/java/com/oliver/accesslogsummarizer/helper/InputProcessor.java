package com.oliver.accesslogsummarizer.helper;

import java.util.List;
import java.util.Optional;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.parser.AccessLogParser;
import com.oliver.accesslogsummarizer.parser.SimpleLogParser;
import com.oliver.accesslogsummarizer.reader.FileStream;
import com.oliver.accesslogsummarizer.reader.InputStream;

public class InputProcessor {

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
		List<Metric> metrics = parser.parseLog(input.getStream(options.getFileName()), options);
		return Optional.ofNullable(metrics);
	}

}
