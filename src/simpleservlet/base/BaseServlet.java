package simpleservlet.base;

import java.nio.file.*;

import javax.servlet.*;
import javax.servlet.http.*;

public abstract class BaseServlet extends HttpServlet {
	protected ServletContext context;

	protected String getRealPath(String... parts) {
		String path;
		path = Paths.get("WEB-INF", parts).toString();
		path = getServletContext().getRealPath(path);
		return path;
	}

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
