package simpleservlet.util;

import java.io.*;

import javax.servlet.*;

public class RequestStreamWrapper extends ServletInputStream {
	private final ByteArrayInputStream stream;

	public RequestStreamWrapper(byte[] content) {
		super();
		stream = new ByteArrayInputStream(content);
	}

	public int read(byte[] b) throws IOException {
		return stream.read(b);
	}

	public int read(byte[] b, int off, int len) {
		return stream.read(b, off, len);
	}

	public long skip(long n) {
		return stream.skip(n);
	}

	public int available() throws IOException {
		return stream.available();
	}

	public void close() throws IOException {
		super.close();
		stream.close();
	}

	public synchronized void mark(int readlimit) {
		stream.mark(readlimit);
	}

	public synchronized void reset() {
		stream.reset();
	}

	public boolean markSupported() {
		return stream.markSupported();
	}

	public int read() {
		return stream.read();
	}
}
