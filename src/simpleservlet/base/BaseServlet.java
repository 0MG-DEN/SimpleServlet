package simpleservlet.base;

import javax.servlet.*;
import javax.servlet.http.*;

public abstract class BaseServlet extends HttpServlet {
	protected ServletContext context;

	protected void log(String format, Object... args) {
		String message = String.format(format, args);
		super.log(message);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		context = getServletContext();
	}
}
