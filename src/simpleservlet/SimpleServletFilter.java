package simpleservlet;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import simpleservlet.util.*;

public class SimpleServletFilter implements Filter {
	public static final Path DEFAULT_FOLDER = Paths.get(System.getProperty("user.dir"));
	public static final int DEFAULT_BUFFER_SIZE = 1024;

	public static final String QUERY_PATH_ATTR = "queryPath";
	public static final String HEADERS_PATH_ATTR = "headersPath";
	public static final String CONTENT_PATH_ATTR = "contentPath";
	public static final String FILTERED_ATTR = "filtered";

	private static Date getDate() {
		return new Date();
	}

	private static String getFilename(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS");
		return format.format(date);
	}

	private Path folder = DEFAULT_FOLDER;
	private int bufferSize = DEFAULT_BUFFER_SIZE;

	private void initFolder(FilterConfig config) {
		String value = config.getInitParameter("folder");
		ServletContext context = config.getServletContext();

		if (value == null) {
			context.log("Parameter 'folder' not found");
			return;
		}

		File folder = Paths.get(value).toFile();

		if (!folder.exists() || !folder.isDirectory()) {
			context.log("Parameter 'folder' not found or is not directory");
			return;
		}

		this.folder = folder.toPath();
	}

	private void initBufferSize(FilterConfig config) {
		String value = config.getInitParameter("buffer-size");
		ServletContext context = config.getServletContext();

		if (value == null) {
			context.log("Parameter 'buffer-size' not found");
			return;
		}

		int bufferSize;

		try {
			bufferSize = Integer.parseUnsignedInt(value);
		} catch (NumberFormatException e) {
			context.log("Parameter 'buffer-size' is not a number", e);
			return;
		}

		if (bufferSize < 1) {
			context.log("Parameter 'buffer-size' is not a valid number");
			return;
		}

		this.bufferSize = bufferSize;
	}

	public void init(FilterConfig config) {
		initFolder(config);
		initBufferSize(config);
	}

	public void destroy() {
		// TODO:
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Date date = getDate();
		String filename = getFilename(date);

		RequestWrapper wrapper = new RequestWrapper((HttpServletRequest) request, bufferSize);
		RequestWriter writer = new RequestWriter(wrapper, filename, folder, bufferSize);

		String queryPath = writer.writeQuery();
		wrapper.setAttribute(QUERY_PATH_ATTR, queryPath);

		String headersPath = writer.writeHeaders();
		wrapper.setAttribute(HEADERS_PATH_ATTR, headersPath);

		String contentPath = writer.writeContent();
		wrapper.setAttribute(CONTENT_PATH_ATTR, contentPath);

		wrapper.setAttribute(FILTERED_ATTR, true);

		chain.doFilter(wrapper, response);
	}
}
