package com.oliver.accesslogsummarizer.reader;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileStream implements InputStream<String> {

	private static final Logger logger = LogManager.getLogger(FileStream.class);

	@Override
	public Stream<String> getStream(String source) {
		Stream<String> stream = null;

		try {
			stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8);
			return stream;
		} catch (MalformedInputException ex) {
			logger.error("Character Set Error, Expected Encoding is UTF-8", ex);
		} catch (IOException ex) {
			logger.error("Error Reding file", ex);
		}

		throw new IllegalStateException(
				"An Error occured, program cannot continue. Kindly check the log file for details.");
	}

}
