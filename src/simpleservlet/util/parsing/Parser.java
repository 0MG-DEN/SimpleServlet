package simpleservlet.util.parsing;

import java.io.*;

import simpleservlet.util.parsing.core.*;

public class Parser {
	private final BufferedReader reader;
	private final BufferedWriter writer;
	private final TransitionMap<Character> map;

	public Parser(BufferedReader reader, BufferedWriter writer) throws StateException {
		this.reader = reader;
		this.writer = writer;
		this.map = createMap();
	}

	private TransitionMap<Character> createMap() throws StateException {
		TransitionMap<Character> map = new TransitionMap<Character>();

		// Default actions.
		TransitionAction<Character> add = (s, sh, i, ns) -> sh.addItem(i);
		TransitionAction<Character> clear = (s, sh, i, ns) -> sh.clearItems();
		TransitionAction<Character> process = (s, sh, i, ns) -> {
			sh.addItem(i);
			while (sh.hasItems())
				writer.write(sh.popItem());
			// If parsed image but without closing '/>'.
			if (sh.hasFlag(ParserFlag.TAG_IMG) && !sh.hasFlag(ParserFlag.TAG_IMG_1))
				writer.write("</img>");
			// If parsed video but without closing '/>'.
			if (sh.hasFlag(ParserFlag.TAG_VIDEO) && !sh.hasFlag(ParserFlag.TAG_VIDEO_1))
				writer.write("</video>");
		};

		// ParserState.NONE
		map.add(ParserState.NONE, '<', ParserState.TAG_START, add);
		map.add(ParserState.NONE, ParserState.NONE, clear);

		// ParserState.TAG_START
		map.add(ParserState.TAG_START, 'i', ParserState.TAG_IMG_I, add);
		map.add(ParserState.TAG_START, 'v', ParserState.TAG_VIDEO_V, add);
		map.add(ParserState.TAG_START, ParserState.NONE, clear);

		// Parsing of image tag.
		{
			// ParserState.TAG_IMG_I
			map.add(ParserState.TAG_IMG_I, 'm', ParserState.TAG_IMG_M, add);
			map.add(ParserState.TAG_IMG_I, ParserState.NONE, clear);

			// ParserState.TAG_IMG_M
			map.add(ParserState.TAG_IMG_M, 'g', ParserState.TAG_IMG_G, add);
			map.add(ParserState.TAG_IMG_M, ParserState.NONE, clear);

			// ParserState.TAG_IMG_G
			map.add(ParserState.TAG_IMG_G, ' ', ParserState.TAG_INNER, (s, sh, i, ns) -> {
				sh.addFlag(ParserFlag.TAG_IMG);
				add.invoke(s, sh, i, ns);
			});
			map.add(ParserState.TAG_IMG_G, ParserState.NONE, clear);
		}

		// Parsing of video tag.
		{
			// ParserState.TAG_VIDEO_V
			map.add(ParserState.TAG_VIDEO_V, 'i', ParserState.TAG_VIDEO_I, add);
			map.add(ParserState.TAG_VIDEO_V, ParserState.NONE, clear);

			// ParserState.TAG_VIDEO_I
			map.add(ParserState.TAG_VIDEO_I, 'd', ParserState.TAG_VIDEO_D, add);
			map.add(ParserState.TAG_VIDEO_I, ParserState.NONE, clear);

			// ParserState.TAG_VIDEO_D
			map.add(ParserState.TAG_VIDEO_D, 'e', ParserState.TAG_VIDEO_E, add);
			map.add(ParserState.TAG_VIDEO_D, ParserState.NONE, clear);

			// ParserState.TAG_VIDEO_E
			map.add(ParserState.TAG_VIDEO_E, 'o', ParserState.TAG_VIDEO_O, add);
			map.add(ParserState.TAG_VIDEO_E, ParserState.NONE, clear);

			// ParserState.TAG_VIDEO_O
			map.add(ParserState.TAG_VIDEO_O, ' ', ParserState.TAG_INNER, (s, sh, i, ns) -> {
				sh.addFlag(ParserFlag.TAG_VIDEO);
				add.invoke(s, sh, i, ns);
			});
			map.add(ParserState.TAG_VIDEO_O, ParserState.NONE, clear);
		}

		// ParserState.TAG_INNER
		map.add(ParserState.TAG_INNER, '/', ParserState.TAG_CLOSE, (s, sh, i, ns) -> {
			if (sh.hasFlag(ParserFlag.TAG_IMG))
				sh.addFlag(ParserFlag.TAG_IMG_1);
			if (sh.hasFlag(ParserFlag.TAG_VIDEO))
				sh.addFlag(ParserFlag.TAG_VIDEO_1);
			add.invoke(s, sh, i, ns);
		});
		map.add(ParserState.TAG_INNER, '>', ParserState.NONE, (s, sh, i, ns) -> {
			process.invoke(s, sh, i, ns);
			sh.removeFlag(ParserFlag.TAG_IMG);
			sh.removeFlag(ParserFlag.TAG_VIDEO);
		});
		map.add(ParserState.TAG_INNER, ParserState.TAG_INNER, add);

		// ParserState.TAG_CLOSE
		map.add(ParserState.TAG_CLOSE, '>', ParserState.NONE, (s, sh, i, ns) -> {
			process.invoke(s, sh, i, ns);
			sh.removeFlag(ParserFlag.TAG_IMG);
			sh.removeFlag(ParserFlag.TAG_VIDEO);
			sh.removeFlag(ParserFlag.TAG_IMG_1);
			sh.removeFlag(ParserFlag.TAG_VIDEO_1);
		});
		map.add(ParserState.TAG_CLOSE, ParserState.TAG_INNER, (s, sh, i, ns) -> {
			add.invoke(s, sh, i, ns);
			sh.removeFlag(ParserFlag.TAG_IMG_1);
			sh.removeFlag(ParserFlag.TAG_VIDEO_1);
		});

		return map;
	}

	public void parse() throws StateException, IOException {
		StateHandler<Character> stateHandler = new StateHandler<Character>(ParserState.NONE);
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
