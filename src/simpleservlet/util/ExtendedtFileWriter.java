package simpleservlet.util;

import java.io.*;
import java.util.*;

/**
 * Extends {@link java.io.BufferedOutputStream} with methods to write streams,
 * strings and formatted text.
 */
public class ExtendedtFileWriter extends BufferedOutputStream {
	/**
	 * Create new writer with specific underlying stream.
	 *
	 * @param out - underlying stream.
	 */
	public ExtendedtFileWriter(OutputStream out) {
		super(out);
	}

	/**
	 * Write stream by parts of {@code 0x1000} bytes.
	 *
	 * @param stream - stream to read from.
	 * @throws IOException if read, write or flush fails.
	 */
	public void write(InputStream stream) throws IOException {
		int length;
		byte[] buffer = new byte[0x1000];
		while ((length = stream.read(buffer)) != -1)
			write(buffer, 0, length);
		flush();
	}

	/**
	 * Write content of string to stream.
	 *
	 * @param str - string to append characters of.
	 * @throws IOException - if write operation fails.
	 */
	public void append(String str) throws IOException {
		write(str.getBytes());
	}

	/**
	 * Write content of string to stream and add new line.
	 *
	 * @param str - string to append characters of.
	 * @throws IOException - if write operation fails.
	 */
	public void appendLine(String str) throws IOException {
		append(str);
		append("\r\n");
	}

	/**
	 * Write content of formatted string to stream.
	 *
	 * @param format - string to format and then append characters of.
	 * @param args   - arguments to format string with.
	 * @throws IOException - if write operation fails.
	 */
	public void appendFormat(String format, Object... args) throws IOException {
		appendLine(String.format(format, args));
	}

	/**
	 * Write content of joined strings to stream.
	 *
	 * @param delimiter - delimited to use when joining strings.
	 * @param elements  - elements to join.
	 * @throws IOException - if write operation fails.
	 */
	public void appendJoin(String delimiter, String... elements) throws IOException {
		appendLine(String.join(delimiter, elements));
	}

	/**
	 * Write content of joined strings to stream.
	 *
	 * @param delimiter - delimited to use when joining strings.
	 * @param elements  - elements to join.
	 * @throws IOException - if write operation fails.
	 */
	public void appendJoin(String delimiter, Enumeration<String> elements) throws IOException {
		ArrayList<String> list = Collections.list(elements);
		appendLine(String.join(delimiter, list));
		list.clear();
	}
}
