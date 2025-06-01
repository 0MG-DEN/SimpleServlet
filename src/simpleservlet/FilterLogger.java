package simpleservlet;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import simpleservlet.base.*;
import simpleservlet.util.*;

/**
 * <p>
 * Filter that saves query, headers and content of processed request. Further
 * chain then receives the {@link simpleservlet.util.RequestWrapper} which
 * serves as proxy for filters to get request's content.
 * <p>
 * Request will have following attributes populated:
 * <ul>
 * <li>{@code queryPath} - absolute path to query file;</li>
 * <li>{@code headersPath} - absolute path to headers file;</li>
 * <li>{@code contentPath} - absolute path to content file;</li>
 * </ul>
 * <p>
 * For multipart requests each part is saved separately into different file. In
 * this case {@code contentPath} points to empty file.
 *
 * @see simpleservlet.util.RequestWriter
 */
public class FilterLogger extends BaseFilter {
	private Path folder;

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);

		String value = config.getInitParameter("folder");
		if (value == null)
			value = System.getProperty("user.dir");

		folder = Paths.get(value);
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS").format(new Date());
		RequestWriter writer = new RequestWriter(request, filename, folder);

		String queryPath = writer.writeQuery();
		request.setAttribute("queryPath", queryPath);

		String headersPath = writer.writeHeaders();
		request.setAttribute("headersPath", headersPath);

		writer.writeContentParts();

		String contentPath = writer.writeContent();
		request.setAttribute("contentPath", contentPath);

		// Request writer has already read content, so we can't get it anymore.
		// But we have it saved as a file so wrapper can return file's content.
		RequestWrapper wrapper = new RequestWrapper(request, contentPath);
		chain.doFilter(wrapper, response);
	}
}
