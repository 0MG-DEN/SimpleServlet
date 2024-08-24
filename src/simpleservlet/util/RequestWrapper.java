package simpleservlet.util;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class RequestWrapper extends HttpServletRequestWrapper {
	private final byte[] content;

	public RequestWrapper(HttpServletRequest request, int bufferSize) throws IOException {
		super(request);

		ServletInputStream inputStream = request.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte[] buffer = new byte[bufferSize];
		int length;

		while ((length = inputStream.read(buffer)) != -1)
			outputStream.write(buffer, 0, length);

		outputStream.flush();
		content = outputStream.toByteArray();

		inputStream.close();
		outputStream.close();
	}

	public ServletInputStream getInputStream() {
		return new RequestStreamWrapper(content);
	}
}
