package com.oliver.accesslogsummarizer;

import java.util.List;

import com.oliver.accesslogsummarizer.beans.Metric;
import com.oliver.accesslogsummarizer.beans.ParsingOptions;
import com.oliver.accesslogsummarizer.helper.CommandLineArgumentParser;
import com.oliver.accesslogsummarizer.helper.InputProcessor;
import com.oliver.accesslogsummarizer.helper.ReportHelper;

public class Main {

	public static void main(String[] args) {

		if (args.length < 4) {
			throw new IllegalArgumentException("Not Enough Arguments Passed to the method");
		}

		ParsingOptions options = CommandLineArgumentParser.ParseArguments(args);

		List<Metric> metrics = InputProcessor
				.getInstance()
				.processAccessLog(options)
				.orElseThrow(() -> new IllegalStateException("Processing failed"));

		ReportHelper.MakeReport(metrics, options);

	}

}
