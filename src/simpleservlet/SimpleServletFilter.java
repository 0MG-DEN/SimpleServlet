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

	private Path folder = Paths.get(System.getProperty("user.dir"));;

	public void init(FilterConfig config) {
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

	public void destroy() {
		// TODO:
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS").format(new Date());

		RequestWriter writer = new RequestWriter((HttpServletRequest) request, filename, this.folder);

		String queryPath = writer.writeQuery();
		request.setAttribute(QUERY_PATH_ATTR, queryPath);

		String headersPath = writer.writeHeaders();
		request.setAttribute(HEADERS_PATH_ATTR, headersPath);

		String contentPath = writer.writeContent();
		request.setAttribute(CONTENT_PATH_ATTR, contentPath);

		RequestWrapper wrapper = new RequestWrapper((HttpServletRequest) request, contentPath);

		chain.doFilter(wrapper, response);
	}
}
