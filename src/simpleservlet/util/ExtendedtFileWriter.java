package simpleservlet.util;

import java.io.*;
import java.util.*;

public class ExtendedtFileWriter extends BufferedOutputStream {
	public ExtendedtFileWriter(File file) throws FileNotFoundException {
		super(new FileOutputStream(file, true));
	}

	public void write(InputStream stream) throws IOException {
		int length;
		byte[] buffer = new byte[0x1000];
		while ((length = stream.read(buffer)) != -1)
			write(buffer, 0, length);
		flush();
	}

	public void append(String str) throws IOException {
		write(str.getBytes());
	}

	public void appendLine(String str) throws IOException {
		append(str);
		append("\r\n");
	}

	public void appendFormat(String format, Object... args) throws IOException {
		appendLine(String.format(format, args));
	}

	public void appendJoin(String delimiter, String... elements) throws IOException {
		appendLine(String.join(delimiter, elements));
	}

	public void appendJoin(String delimiter, Enumeration<String> elements) throws IOException {
		ArrayList<String> list = Collections.list(elements);
		appendLine(String.join(delimiter, list));
		list.clear();
	}
}
