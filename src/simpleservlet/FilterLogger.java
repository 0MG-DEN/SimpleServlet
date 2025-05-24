package simpleservlet;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import simpleservlet.base.*;
import simpleservlet.util.*;

public class FilterLogger extends BaseFilter {
	private Path folder;

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);

		String value;
		value = config.getInitParameter("folder");
		value = (value == null) ? System.getProperty("user.dir") : value;

		folder = Paths.get(value);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS").format(new Date());
		RequestWriter writer = new RequestWriter((HttpServletRequest) request, filename, folder);

		String queryPath = writer.writeQuery();
		request.setAttribute("queryPath", queryPath);

		String headersPath = writer.writeHeaders();
		request.setAttribute("headersPath", headersPath);

		writer.writeContentParts();

		String contentPath = writer.writeContent();
		request.setAttribute("contentPath", contentPath);

		// Request writer has already read content, so we can't get it anymore.
		// But we have it saved as a file so wrapper can return file's content.
		RequestWrapper wrapper = new RequestWrapper((HttpServletRequest) request, contentPath);
		chain.doFilter(wrapper, response);
	}
}
