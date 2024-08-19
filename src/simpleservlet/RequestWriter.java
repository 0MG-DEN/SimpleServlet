package simpleservlet;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class RequestWriter {
	private final HttpServletRequest request;

	public RequestWriter(HttpServletRequest request) {
		this.request = request;
	}

	public void writeQuery(Path path) throws IOException {
		Map<String, String[]> map = request.getParameterMap();

		if (map != null) {
			FileOutputStream stream = new FileOutputStream(path.toFile(), true);
			OutputStreamWriter writer = new OutputStreamWriter(stream);

			for (String key : map.keySet()) {
				writer.append(key);
				writer.append(":\r\n");

				for (String value : map.get(key)) {
					writer.append(value);
					writer.append(";\r\n");
				}
			}

			writer.flush();
			writer.close();
			stream.flush();
			stream.close();
		}
	}

	public void writeHeaders(Path path) throws IOException {
		FileOutputStream stream = new FileOutputStream(path.toFile(), true);
		OutputStreamWriter writer = new OutputStreamWriter(stream);

		List<String> names = Collections.list(request.getHeaderNames());

		for (String name : names) {
			writer.append(name);
			writer.append(":\r\n");

			List<String> values = Collections.list(request.getHeaders(name));

			for (String value : values) {
				writer.append(value);
				writer.append(";\r\n");
			}

			values.clear();
		}

		names.clear();

		writer.flush();
		writer.close();
		stream.flush();
		stream.close();
	}

	public void writeContent(Path path) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		FileOutputStream outputStream = new FileOutputStream(path.toFile(), true);

		byte[] buffer = new byte[1024];
		int length;

		while ((length = inputStream.read(buffer)) != -1)
			outputStream.write(buffer, 0, length);

		inputStream.close();
		outputStream.flush();
		outputStream.close();
	}
}
