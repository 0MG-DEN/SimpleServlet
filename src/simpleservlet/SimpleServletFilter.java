package simpleservlet;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import simpleservlet.util.*;

public class SimpleServletFilter implements Filter {
	public static final String QUERY_PATH_ATTR = "queryPath";
	public static final String HEADERS_PATH_ATTR = "headersPath";
	public static final String CONTENT_PATH_ATTR = "contentPath";

	private ServletContext context;
	private Path folder;

	private void log(String format, Object... args) {
		String log = String.format(format, args);
		context.log(log);
	}

	public void init(FilterConfig config) {
		this.context = config.getServletContext();
		this.folder = Paths.get(System.getProperty("user.dir"));

		String value = config.getInitParameter("folder");
		log("Parameter 'folder': %s", value);

		if (value == null) {
			log("Parameter 'folder' not found");
			log("Using default folder: %s", folder);
			return;
		}

		File file = Paths.get(value).toFile();

		if (!file.exists() || !file.isDirectory()) {
			log("Parameter 'folder' not found or is not directory");
			log("Using default folder: %s", folder);
			return;
		}

		this.folder = file.toPath();
		log("Using new folder: %s", folder);
	}

	public void destroy() {
		// TODO:
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log("Filtering request from %s", request.getRemoteAddr());

		String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS").format(new Date());
		log("Filename %s", filename);

		RequestWriter writer = new RequestWriter((HttpServletRequest) request, filename, folder);

		String queryPath = writer.writeQuery();
		request.setAttribute(QUERY_PATH_ATTR, queryPath);
		log("Attribute '%s': %s", QUERY_PATH_ATTR, queryPath);

		String headersPath = writer.writeHeaders();
		request.setAttribute(HEADERS_PATH_ATTR, headersPath);
		log("Attribute '%s': %s", HEADERS_PATH_ATTR, headersPath);

		String contentPath = writer.writeContent();
		request.setAttribute(CONTENT_PATH_ATTR, contentPath);
		log("Attribute '%s': %s", CONTENT_PATH_ATTR, contentPath);

		log("Creating wrapper");
		RequestWrapper wrapper = new RequestWrapper((HttpServletRequest) request, contentPath);

		log("Passing to filter chain");
		chain.doFilter(wrapper, response);
	}
}
