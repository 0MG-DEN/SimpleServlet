package simpleservlet.util;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class RequestWrapper extends HttpServletRequestWrapper {
	private final String contentPath;

	public RequestWrapper(HttpServletRequest request, String contentPath) {
		super(request);
		this.contentPath = contentPath;
	}

	public ServletInputStream getInputStream() throws FileNotFoundException {
		return new RequestStreamWrapper(contentPath);
	}
}
