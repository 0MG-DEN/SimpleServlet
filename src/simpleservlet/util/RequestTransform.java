package simpleservlet.util;

import java.io.*;

import javax.servlet.http.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import simpleservlet.util.parsing.*;

public class RequestTransform implements AutoCloseable {
	private final BufferedReader reader;
	private final BufferedWriter writer;
	private final Transformer transform;

	public RequestTransform(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException, TransformerException {
		this.reader = request.getReader();
		this.writer = new BufferedWriter(response.getWriter());

		TransformerFactory factory = TransformerFactory.newInstance();
		Source source = new StreamSource(path);
		this.transform = factory.newTransformer(source);
	}

	public void close() throws IOException {
		reader.close();
		writer.close();
	}

	public void transform() throws IOException, TransformerException {
		String parsed;

		try (StringWriter sw = new StringWriter(); BufferedWriter bw = new BufferedWriter(sw)) {
			bw.write("<media>");
			Parser parser = new Parser(reader, bw);
			parser.parse();
			bw.write("</media>");

			bw.flush();
			sw.flush();
			parsed = sw.toString();
		} catch (ParserException e) {
			throw new TransformerException(e);
		}

		Source source = new StreamSource(new StringReader(parsed));
		Result result = new StreamResult(writer);
		transform.transform(source, result);
	}
}
