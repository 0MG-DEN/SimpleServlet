package simpleservlet;

import java.io.IOException;
import java.util.regex.*;

import javax.servlet.*;
import javax.servlet.http.*;

import simpleservlet.base.*;

public class FilterContent extends BaseFilter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String uri = httpRequest.getRequestURI();

		Pattern pattern = Pattern.compile("^/simpleservlet/(js/[\\w\\-]+\\.js|css/[\\w\\-]+\\.css)$");
		Matcher matcher = pattern.matcher(uri);

		if (matcher.find()) {
			String path = "/WEB-INF/" + matcher.group(1);
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}

		String message = String.format("Content not found %s", uri);
		throw new ServletException(message);
	}
}
