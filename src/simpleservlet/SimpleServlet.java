package simpleservlet;

import javax.servlet.http.*;

public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void log(String format, Object... args) {
		String message = String.format(format, args);
		super.log(message);
	}
}
