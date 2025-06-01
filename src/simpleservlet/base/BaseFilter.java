package simpleservlet.base;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public abstract class BaseFilter implements Filter {
	private ServletContext context;

	protected void log(String format, Object... args) {
		String message = String.format(format, args);
		context.log(message);
	}

	protected abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r1 = (HttpServletRequest) request;
		HttpServletResponse r2 = (HttpServletResponse) response;
		doFilter(r1, r2, chain);
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
	}
}
