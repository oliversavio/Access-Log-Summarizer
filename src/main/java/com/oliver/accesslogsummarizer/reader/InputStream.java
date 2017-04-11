package com.oliver.accesslogsummarizer.reader;

import java.util.stream.Stream;

public interface InputStream<T> {
	public Stream<T> getStream(String source);
}
