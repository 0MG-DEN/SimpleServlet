package simpleservlet.util;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Wrapper that creates {@link simpleservlet.util.RequestStreamWrapper} when
 * request's content stream or reader is asked for. Path to content file is
 * passed to constructor.
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	private final String contentPath;

	/**
	 * Create new wrapper with specific content file.
	 *
	 * @param request     - request to wrap.
	 * @param contentPath - content file to return input stream or reader for.
	 */
	public RequestWrapper(HttpServletRequest request, String contentPath) {
		super(request);
		this.contentPath = contentPath;
	}

	@Override
	public ServletInputStream getInputStream() throws FileNotFoundException {
		return new RequestStreamWrapper(contentPath);
	}

	@Override
	public BufferedReader getReader() throws FileNotFoundException {
		return new BufferedReader(new FileReader(contentPath));
	}
}
