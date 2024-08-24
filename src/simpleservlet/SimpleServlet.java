package simpleservlet;

import java.io.*;
import java.nio.file.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = Paths.get("WEB-INF", "jsp", "result.jsp").toString();
		request.getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}
}
