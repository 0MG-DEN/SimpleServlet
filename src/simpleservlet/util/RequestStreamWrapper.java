package simpleservlet.util;

import java.io.*;

import javax.servlet.*;

public class RequestStreamWrapper extends ServletInputStream {
	private final FileInputStream stream;

	public RequestStreamWrapper(String contentPath) throws FileNotFoundException {
		super();
		this.stream = new FileInputStream(contentPath);
	}

	@Override
	public int read(byte[] b) throws IOException {
		return stream.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return stream.read(b, off, len);
	}

	@Override
	public long skip(long n) throws IOException {
		return stream.skip(n);
	}

	@Override
	public int available() throws IOException {
		return stream.available();
	}

	@Override
	public void close() throws IOException {
		super.close();
		stream.close();
	}

	@Override
	public synchronized void mark(int readlimit) {
		stream.mark(readlimit);
	}

	@Override
	public synchronized void reset() throws IOException {
		stream.reset();
	}

	@Override
	public boolean markSupported() {
		return stream.markSupported();
	}

	@Override
	public int read() throws IOException {
		return stream.read();
	}
}
