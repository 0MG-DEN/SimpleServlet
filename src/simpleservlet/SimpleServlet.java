package simpleservlet;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Date getDate() {
		return new Date();
	}

	private static String getFilename(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS");
		return format.format(date);
	}

	private static Path getQueryFile(Path directory, String name) {
		String filename = String.format("%s.query", name);
		return directory.resolve(filename);
	}

	private static Path getHeadersFile(Path directory, String name) {
		String filename = String.format("%s.headers", name);
		return directory.resolve(filename);
	}

	private static Path getContentFile(Path directory, String name) {
		String filename = String.format("%s.content", name);
		return directory.resolve(filename);
	}

	private Path getDirectory() {
		String value = getServletConfig().getInitParameter("folder");
		String defaultValue = System.getProperty("user.dir");

		if (value == null) {
			log("Parameter 'folder' not found");
			return Paths.get(defaultValue);
		}

		File directory = Paths.get(value).toFile();

		if (!directory.exists() || !directory.isDirectory()) {
			log("Parameter 'folder' not found or is not directory");
			return Paths.get(defaultValue);
		}

		return directory.toPath();
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Date date = getDate();
		String filename = getFilename(date);

		Path directory = getDirectory();
		RequestWriter writer = new RequestWriter(request);

		Path queryPath = getQueryFile(directory, filename);
		writer.writeQuery(queryPath);

		Path headersPath = getHeadersFile(directory, filename);
		writer.writeHeaders(headersPath);

		Path contentPath = getContentFile(directory, filename);
		writer.writeContent(contentPath);

		writeResponse(response, queryPath, headersPath, contentPath);
	}

	private void writeResponse(HttpServletResponse response, Path queryPath, Path headersPath, Path contentPath)
			throws IOException {
		response.setContentType("text/plain");

		PrintWriter writer = response.getWriter();

		writer.format("%s\r\n", queryPath);
		writer.format("%s\r\n", headersPath);
		writer.format("%s\r\n", contentPath);

		writer.flush();
		writer.close();
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
