package simpleservlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.transform.*;

import simpleservlet.base.*;
import simpleservlet.util.*;

public class ServletDumpMedia extends BaseServlet {
	private void transform(HttpServletRequest request, HttpServletResponse response) throws IOException, TransformerException {
		response.setContentType("text/html");

		String path = getRealPath("xsl", "dumpmedia.xsl");
		log("Transformation: %s", path);

		try (RequestTransform t = new RequestTransform(request, response, path)) {
			log("Transforming request content");
			t.transform();
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
