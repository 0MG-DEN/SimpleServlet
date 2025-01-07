package simpleservlet.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class RequestWriter {
	private final HttpServletRequest request;
	private final String filename;
	private final Path folder;

	public RequestWriter(HttpServletRequest request, String filename, Path folder) {
		this.request = request;
		this.filename = filename;
		this.folder = folder;
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

	private File getPartContentFile(int index) {
		String name = String.format("%s.content%d", filename, index);
		return folder.resolve(name).toFile();
	}

	private void writeStream(File file, Supplier<InputStream, IOException> supplier) throws IOException {
		try (ExtendedtFileWriter writer = new ExtendedtFileWriter(file); InputStream stream = supplier.get()) {
			writer.write(stream);
		}
	}

	public String writeQuery() throws IOException {
		File file = getQueryFile();

		Map<String, String[]> map = request.getParameterMap();

		if (map != null) {
			try (ExtendedtFileWriter writer = new ExtendedtFileWriter(file)) {
				for (String key : map.keySet()) {
					writer.appendFormat("%s:", key);
					writer.appendJoin(";", map.get(key));
				}
			}
		}

		return file.getAbsolutePath();
	}

	public String writeHeaders() throws IOException {
		File file = getHeadersFile();

		List<String> names = Collections.list(request.getHeaderNames());

		try (ExtendedtFileWriter writer = new ExtendedtFileWriter(file)) {
			for (String name : names) {
				writer.appendFormat("%s:", name);
				writer.appendJoin(";", request.getHeaders(name));
			}
		}

		names.clear();

		return file.getAbsolutePath();
	}

	public String writeContent() throws IOException {
		File file = getContentFile();
		writeStream(file, () -> request.getInputStream());

		return file.getAbsolutePath();
	}

	public void writeContentParts() throws IOException, ServletException {
		// Multipart content type may contain boundary so check for prefix.
		String contentType = request.getContentType();
		if (contentType == null || !contentType.startsWith("multipart/form-data"))
			return;

		Collection<Part> parts = request.getParts();
		if (parts == null || parts.isEmpty())
			return;

		int index = 0;
		for (Part part : parts) {
			File file = getPartContentFile(index++);
			writeStream(file, () -> part.getInputStream());
		}
	}
}
