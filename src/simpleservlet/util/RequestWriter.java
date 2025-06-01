package simpleservlet.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Provides methods to save request's headers, query and content to files.
 * Additionally supports multipart requests, each part is saved separately.
 */
public class RequestWriter {
	private final HttpServletRequest request;
	private final String filename;
	private final Path folder;

	/**
	 * Create new writer for request with specific base filename and folder.
	 *
	 * @param request  - request to get data from.
	 * @param filename - base filename to use for all files.
	 * @param folder   - folder to save files in.
	 */
	public RequestWriter(HttpServletRequest request, String filename, Path folder) {
		this.request = request;
		this.filename = filename;
		this.folder = folder;
	}

	/**
	 * Get query file to write request's query to.
	 *
	 * @return Query {@link File} resolved against folder.
	 */
	private File getQueryFile() {
		String name = String.format("%s.query", filename);
		return folder.resolve(name).toFile();
	}

	/**
	 * Get headers file to write request's headers to.
	 *
	 * @return Headers {@link File} resolved against folder.
	 */
	private File getHeadersFile() {
		String name = String.format("%s.headers", filename);
		return folder.resolve(name).toFile();
	}

	/**
	 * Get content file to write request's content to.
	 *
	 * @return Content {@link File} resolved against folder.
	 */
	private File getContentFile() {
		String name = String.format("%s.content", filename);
		return folder.resolve(name).toFile();
	}

	/**
	 * Get content part file to write request part's content to.
	 *
	 * @return Content {@link File} resolved against folder.
	 */
	private File getPartContentFile(int index) {
		String name = String.format("%s.content%d", filename, index);
		return folder.resolve(name).toFile();
	}

	private void writeStream(File file, Supplier<InputStream, IOException> supplier) throws IOException {
		try (InputStream input = supplier.get(); OutputStream output = new FileOutputStream(file)) {
			try (ExtendedtFileWriter writer = new ExtendedtFileWriter(output)) {
				writer.write(input);
			}
		}
	}

	/**
	 * Write query of request to file.
	 *
	 * @return Absolute path to saved file.
	 */
	public String writeQuery() throws IOException {
		File file = getQueryFile();

		Map<String, String[]> map = request.getParameterMap();

		if (map != null) {
			try (OutputStream output = new FileOutputStream(file); ExtendedtFileWriter writer = new ExtendedtFileWriter(output)) {
				for (String key : map.keySet()) {
					writer.appendFormat("%s:", key);
					writer.appendJoin(";", map.get(key));
				}
			}
		}

		return file.getAbsolutePath();
	}

	/**
	 * Write headers of request to file.
	 *
	 * @return Absolute path to saved file.
	 */
	public String writeHeaders() throws IOException {
		File file = getHeadersFile();

		List<String> names = Collections.list(request.getHeaderNames());

		try (OutputStream output = new FileOutputStream(file); ExtendedtFileWriter writer = new ExtendedtFileWriter(output)) {
			for (String name : names) {
				writer.appendFormat("%s:", name);
				writer.appendJoin(";", request.getHeaders(name));
			}
		}

		names.clear();

		return file.getAbsolutePath();
	}

	/**
	 * Write content of request to file.
	 *
	 * @return Absolute path to saved file.
	 */
	public String writeContent() throws IOException {
		File file = getContentFile();
		writeStream(file, () -> request.getInputStream());

		return file.getAbsolutePath();
	}

	/**
	 * Write parts of multipart request's content to files. Saved files' paths match
	 * one returned by {@link simpleservlet.util.RequestWriter#writeContent} with
	 * index of each part added to file's extension.
	 */
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
