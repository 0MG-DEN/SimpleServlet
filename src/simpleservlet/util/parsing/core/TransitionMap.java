package simpleservlet.util.parsing.core;

import java.io.*;
import java.util.*;

import simpleservlet.util.*;

public class TransitionMap<Item extends Comparable<Item>> {
	private final HashMap<Key, Value> map;

	public TransitionMap() {
		this.map = new HashMap<Key, Value>();
	}

	private Value getOrNull(int state, Item item) {
		Key key = new Key(state, item);
		return map.get(key);
	}

	private Value get(int state) throws StateException {
		Key key = new Key(state, null);
		if (!map.containsKey(key))
			throw StateException.transitionNotFound(state);
		return map.get(key);
	}

	public void add(int state, Item item, int newState, TransitionAction<Item> action) throws StateException {
		Key key = new Key(state, item);
		if (map.containsKey(key))
			throw StateException.transitionAlreadyExists(state, item);
		Value value = new Value(newState, action);
		map.put(key, value);
	}

	public void add(int state, int newState, TransitionAction<Item> action) throws StateException {
		Key key = new Key(state, null);
		if (map.containsKey(key))
			throw StateException.transitionAlreadyExists(state);
		Value value = new Value(newState, action);
		map.put(key, value);
	}

	public void invoke(StateHandler<Item> stateHandler, Item item) throws StateException, IOException {
		int state = stateHandler.getState();
		Value value = getOrNull(state, item);
		if (value == null) // Get default value for state.
			value = get(state);
		stateHandler.setState(value.item1);
		value.item2.invoke(state, stateHandler, item, value.item1);
	}

	class Key extends Pair<Integer, Item> {
		public Key(Integer item1, Item item2) {
			super(item1, item2);
		}
	};

	class Value extends Pair<Integer, TransitionAction<Item>> {
		public Value(Integer item1, TransitionAction<Item> item2) {
			super(item1, item2);
		}
	};
}
