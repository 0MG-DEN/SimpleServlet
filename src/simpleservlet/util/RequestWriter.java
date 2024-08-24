package simpleservlet.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.servlet.http.*;

public class RequestWriter {
	private final HttpServletRequest request;
	private final String filename;
	private final Path folder;
	private final byte[] buffer;

	public RequestWriter(HttpServletRequest request, String filename, Path folder, int bufferSize) {
		this.request = request;
		this.filename = filename;
		this.folder = folder;
		this.buffer = new byte[bufferSize];
	}

	private File getQueryFile() {
		String name = String.format("%s.query", filename);
		return folder.resolve(name).toFile();
	}

	private File getHeadersFile() {
		String name = String.format("%s.headers", filename);
		return folder.resolve(name).toFile();
	}

	private File getContentFile() {
		String name = String.format("%s.content", filename);
		return folder.resolve(name).toFile();
	}

	public String writeQuery() throws IOException {
		File file = getQueryFile();

		Map<String, String[]> map = request.getParameterMap();

		if (map != null) {
			FileOutputStream stream = new FileOutputStream(file, true);
			OutputStreamWriter writer = new OutputStreamWriter(stream);

			for (String key : map.keySet()) {
				writer.append(key);
				writer.append(":\r\n");

				for (String value : map.get(key)) {
					writer.append(value);
					writer.append(";\r\n");
				}
			}

			writer.flush();
			writer.close();
			stream.flush();
			stream.close();
		}

		return file.getAbsolutePath();
	}

	public String writeHeaders() throws IOException {
		File file = getHeadersFile();

		FileOutputStream stream = new FileOutputStream(file, true);
		OutputStreamWriter writer = new OutputStreamWriter(stream);

		List<String> names = Collections.list(request.getHeaderNames());

		for (String name : names) {
			writer.append(name);
			writer.append(":\r\n");

			List<String> values = Collections.list(request.getHeaders(name));

			for (String value : values) {
				writer.append(value);
				writer.append(";\r\n");
			}

			values.clear();
		}

		names.clear();

		writer.flush();
		writer.close();
		stream.flush();
		stream.close();

		return file.getAbsolutePath();
	}

	public String writeContent() throws IOException {
		File file = getContentFile();

		InputStream inputStream = request.getInputStream();
		FileOutputStream outputStream = new FileOutputStream(file, true);

		int length;
		while ((length = inputStream.read(buffer)) != -1)
			outputStream.write(buffer, 0, length);

		inputStream.close();
		outputStream.flush();
		outputStream.close();

		return file.getAbsolutePath();
	}
}
