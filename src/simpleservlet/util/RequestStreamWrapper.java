package simpleservlet.util;

import java.io.*;

import javax.servlet.*;

public class RequestStreamWrapper extends ServletInputStream {
	private final FileInputStream stream;

	public RequestStreamWrapper(String contentPath) throws FileNotFoundException {
		super();
		this.stream = new FileInputStream(contentPath);
	}

	public int read(byte[] b) throws IOException {
		return this.stream.read(b);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		return this.stream.read(b, off, len);
	}

	public long skip(long n) throws IOException {
		return this.stream.skip(n);
	}

	public int available() throws IOException {
		return this.stream.available();
	}

	public void close() throws IOException {
		super.close();
		this.stream.close();
	}

	public synchronized void mark(int readlimit) {
		this.stream.mark(readlimit);
	}

	public synchronized void reset() throws IOException {
		this.stream.reset();
	}

	public boolean markSupported() {
		return this.stream.markSupported();
	}

	public int read() throws IOException {
		return this.stream.read();
	}
}
