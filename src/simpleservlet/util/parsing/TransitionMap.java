package simpleservlet.util.parsing;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class TransitionMap<Item extends Comparable<Item>> {
	private final List<Transition<Item>> map;

	public TransitionMap() {
		this.map = new ArrayList<Transition<Item>>();
	}

	private Transition<Item> getOrNull(Predicate<Transition<Item>> predicate) throws ParserException {
		List<Transition<Item>> list = map.stream().filter(predicate).collect(Collectors.toList());
		try {
			if (list.isEmpty())
				return null;
			if (list.size() > 1)
				throw ParserException.AMBIGIOUS_MAP;
			return list.get(0);
		} finally {
			list.clear();
		}
	}

	public Transition<Item> getOrNull(int state, Item item) throws ParserException {
		Predicate<Transition<Item>> predicate;
		predicate = x -> x.hasState(state) && x.hasItem(item);
		return getOrNull(predicate);
	}

	public Transition<Item> getOrNull(int state) throws ParserException {
		Predicate<Transition<Item>> predicate;
		predicate = x -> x.hasState(state) && x.hasItem(null);
		return getOrNull(predicate);
	}

	public Transition<Item> get(int state, Item item) throws ParserException {
		Transition<Item> transition = getOrNull(state, item);
		if (transition == null)
			throw ParserException.TRANSITION_NOT_FOUND;
		return transition;
	}

	public Transition<Item> get(int state) throws ParserException {
		Transition<Item> transition = getOrNull(state);
		if (transition == null)
			throw ParserException.TRANSITION_NOT_FOUND;
		return transition;
	}

	public void add(int state, Item item, int newState, TransitionAction<Item> action) throws ParserException {
		Transition<Item> transition = getOrNull(state, item);
		if (transition != null)
			throw ParserException.TRANSITION_ALREADY_EXISTS;
		transition = new Transition<Item>(state, item, newState, action);
		map.add(transition);
	}

	public void add(int state, int newState, TransitionAction<Item> action) throws ParserException {
		Transition<Item> transition = getOrNull(state);
		if (transition != null)
			throw ParserException.TRANSITION_ALREADY_EXISTS;
		transition = new Transition<Item>(state, null, newState, action);
		map.add(transition);
	}

	public void invoke(StateHandler stateHandler, Item item) throws ParserException, IOException {
		int state = stateHandler.getState();
		Transition<Item> transition = getOrNull(state, item);
		if (transition == null) // Get default transition for state.
			transition = get(state);
		transition.invoke(stateHandler, item);
	}
}
