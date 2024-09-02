package simpleservlet;

import java.io.*;
import java.nio.file.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.transform.*;

import simpleservlet.util.*;

public class DumpMedia extends SimpleServlet {
	private static final long serialVersionUID = 1L;

	private void transform(HttpServletRequest request, HttpServletResponse response)
			throws IOException, TransformerException {
		String path;
		path = Paths.get("WEB-INF", "xsl", "dumpmedia.xsl").toString();
		path = getServletContext().getRealPath(path);

		response.setContentType("text/html");
		
		log("Transformation: %s", path);
		try (RequestTransform t = new RequestTransform(request, response, path)) {
			log("Transforming request content");
			t.transform();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			transform(request, response);
		} catch (TransformerException e) {
			log(e.getMessage());
			throw new ServletException(e);
		} catch (IOException e) {
			log(e.getMessage());
			throw e;
		}
	}
}
