package com.oliver.accesslogsummarizer.reports;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oliver.accesslogsummarizer.beans.ReportContext;

public abstract class ReportWriter {

	private static final Logger logger = LogManager.getLogger(ReportWriter.class);
	
	public abstract void generateReport(ReportContext context);

	protected void writeToFile(String result, String fileName) {
		Path path = Paths.get(fileName);
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(result);
		} catch (IOException ex) {
			logger.error("Error while writing file to output: ", ex);
		}
	}

}
