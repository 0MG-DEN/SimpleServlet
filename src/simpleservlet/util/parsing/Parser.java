package simpleservlet.util.parsing;

import java.io.*;
import java.util.*;

public class Parser {
	private final BufferedReader reader;
	private final BufferedWriter writer;
	private final TransitionMap<Character> map;

	protected final Queue<Character> content = new LinkedList<Character>();
	protected final StateHandler stateHandler = new StateHandler(State.NONE);

	public Parser(BufferedReader reader, BufferedWriter writer) throws ParserException {
		this.reader = reader;
		this.writer = writer;
		this.map = ParserMap.create(this);
	}

	protected void processContent() throws IOException {
		while (!content.isEmpty())
			writer.write(content.remove());
		// If parsed image but without closing '/>'.
		if (stateHandler.hasState(State.TAG_IMG) && !stateHandler.hasState(State.TAG_IMG_1))
			writer.write("</img>");
		// If parsed video but without closing '/>'.
		if (stateHandler.hasState(State.TAG_VIDEO) && !stateHandler.hasState(State.TAG_VIDEO_1))
			writer.write("</video>");
	}

	public void parse() throws ParserException, IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			int length = line.length();
			for (int i = 0; i < length; ++i) {
				char item = line.charAt(i);
				map.invoke(stateHandler, item);
			}
		}
	}
}
