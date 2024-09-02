package simpleservlet.util.parsing;

class ParserMap extends TransitionMap<Character> {
	public static ParserMap create(Parser parser) throws ParserException {
		ParserMap map = new ParserMap();

		// Default actions.
		TransitionAction<Character> add = (s, i, ns) -> {
			parser.content.add(i);
		};
		TransitionAction<Character> clear = (s, i, ns) -> {
			parser.content.clear();
		};
		TransitionAction<Character> process = (s, i, ns) -> {
			parser.content.add(i);
			parser.processContent();
			parser.content.clear();
		};

		// State.NONE
		map.add(State.NONE, '<', State.TAG_START, add);
		map.add(State.NONE, State.NONE, clear);

		// State.TAG_START
		map.add(State.TAG_START, 'i', State.TAG_IMG_I, add);
		map.add(State.TAG_START, 'v', State.TAG_VIDEO_V, add);
		map.add(State.TAG_START, State.NONE, clear);

		// Parsing of image tag.
		{
			// State.TAG_IMG_I
			map.add(State.TAG_IMG_I, 'm', State.TAG_IMG_M, add);
			map.add(State.TAG_IMG_I, State.NONE, clear);

			// State.TAG_IMG_M
			map.add(State.TAG_IMG_M, 'g', State.TAG_IMG_G, add);
			map.add(State.TAG_IMG_M, State.NONE, clear);

			// State.TAG_IMG_G
			map.add(State.TAG_IMG_G, ' ', State.TAG_INNER, (s, i, ns) -> {
				parser.stateHandler.addState(State.TAG_IMG);
				add.invoke(s, i, ns);
			});
			map.add(State.TAG_IMG_G, State.NONE, clear);
		}

		// Parsing of video tag.
		{
			// State.TAG_VIDEO_V
			map.add(State.TAG_VIDEO_V, 'i', State.TAG_VIDEO_I, add);
			map.add(State.TAG_VIDEO_V, State.NONE, clear);

			// State.TAG_VIDEO_I
			map.add(State.TAG_VIDEO_I, 'd', State.TAG_VIDEO_D, add);
			map.add(State.TAG_VIDEO_I, State.NONE, clear);

			// State.TAG_VIDEO_D
			map.add(State.TAG_VIDEO_D, 'e', State.TAG_VIDEO_E, add);
			map.add(State.TAG_VIDEO_D, State.NONE, clear);

			// State.TAG_VIDEO_E
			map.add(State.TAG_VIDEO_E, 'o', State.TAG_VIDEO_O, add);
			map.add(State.TAG_VIDEO_E, State.NONE, clear);

			// State.TAG_VIDEO_O
			map.add(State.TAG_VIDEO_O, ' ', State.TAG_INNER, (s, i, ns) -> {
				parser.stateHandler.addState(State.TAG_VIDEO);
				add.invoke(s, i, ns);
			});
			map.add(State.TAG_VIDEO_O, State.NONE, clear);
		}

		// State.TAG_INNER
		map.add(State.TAG_INNER, '/', State.TAG_CLOSE, (s, i, ns) -> {
			if (parser.stateHandler.hasState(State.TAG_IMG))
				parser.stateHandler.addState(State.TAG_IMG_1);
			if (parser.stateHandler.hasState(State.TAG_VIDEO))
				parser.stateHandler.addState(State.TAG_VIDEO_1);
			add.invoke(s, i, ns);
		});
		map.add(State.TAG_INNER, '>', State.NONE, (s, i, ns) -> {
			process.invoke(s, i, ns);
			parser.stateHandler.removeState(State.TAG_IMG);
			parser.stateHandler.removeState(State.TAG_VIDEO);
		});
		map.add(State.TAG_INNER, State.TAG_INNER, add);

		// State.TAG_CLOSE
		map.add(State.TAG_CLOSE, '>', State.NONE, (s, i, ns) -> {
			process.invoke(s, i, ns);
			parser.stateHandler.removeState(State.TAG_IMG);
			parser.stateHandler.removeState(State.TAG_VIDEO);
			parser.stateHandler.removeState(State.TAG_IMG_1);
			parser.stateHandler.removeState(State.TAG_VIDEO_1);
		});
		map.add(State.TAG_CLOSE, State.TAG_INNER, (s, i, ns) -> {
			add.invoke(s, i, ns);
			parser.stateHandler.removeState(State.TAG_IMG_1);
			parser.stateHandler.removeState(State.TAG_VIDEO_1);
		});

		return map;
	}
}
