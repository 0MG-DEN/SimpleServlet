package simpleservlet.util;

import java.io.*;

import javax.servlet.http.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import simpleservlet.util.parsing.*;
import simpleservlet.util.parsing.core.*;

/**
 * Uses {@link simpleservlet.parsing.Parser} to parse request's content.
 * Extracted XML is then passed to XSL transformation to form a HTML document
 * which is written to response.
 */
public class RequestTransform implements AutoCloseable {
	private final BufferedReader reader;
	private final BufferedWriter writer;
	private final Transformer transform;

	/**
	 * Create new transform with specific XSL transformation file.
	 *
	 * @param request  - request to parse content of.
	 * @param response - response to write transformed XML as HTML to.
	 * @param path     - absolute path to XSL transformation.
	 */
	public RequestTransform(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, TransformerException {
		this.reader = request.getReader();
		this.writer = new BufferedWriter(response.getWriter());

		TransformerFactory factory = TransformerFactory.newInstance();
		Source source = new StreamSource(path);
		this.transform = factory.newTransformer(source);
	}

	@Override
	public void close() throws IOException {
		reader.close();
		writer.close();
	}

	/**
	 * Parse request's content and pass extracted XML to XSL transformation.
	 */
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
		} catch (StateException e) {
			throw new TransformerException(e);
		}

		Source source = new StreamSource(new StringReader(parsed));
		Result result = new StreamResult(writer);
		transform.transform(source, result);
	}
}
