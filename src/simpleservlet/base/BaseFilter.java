package simpleservlet.base;

import java.io.*;

import javax.servlet.*;

public abstract class BaseFilter implements Filter {
	protected ServletContext context;

	protected void log(String format, Object... args) {
		String message = String.format(format, args);
		context.log(message);
	}

	@Override
	public abstract void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
	}
}
