package simpleservlet.util.parsing.core;

import java.io.*;
import java.util.*;

import simpleservlet.util.*;

/**
 * Holds collection of transitions mapped to pair of state and item. When state
 * handler and next item are passed to, transiton corresponding to this item and
 * handler's state is extracted, handler's state is changed and transition's
 * action is invoked.
 *
 * @param <Item> - objects processed by actions held inside inner collection.
 */
public class TransitionMap<Item extends Comparable<Item>> {
	private final HashMap<Key, Value> map;

	/**
	 * Create new map.
	 */
	public TransitionMap() {
		this.map = new HashMap<Key, Value>();
	}

	/**
	 * Get transition mapped to specific state and item or null if no such
	 * transition exists.
	 *
	 * @param state - input state.
	 * @param item  - input item.
	 * @return Pair of next state and action to perform or null if no transition is
	 *         mapped to passed state and item.
	 */
	private Value getOrNull(int state, Item item) {
		Key key = new Key(state, item);
		return map.get(key);
	}

	/**
	 * Get transition mapped to specific state and item.
	 *
	 * @param state - input state.
	 * @param item  - input item.
	 * @return Pair of next state and action to perform.
	 * @throws StateException if no transition is mapped to passed state and item.
	 */
	private Value get(int state) throws StateException {
		Key key = new Key(state, null);
		if (!map.containsKey(key))
			throw StateException.transitionNotFound(state);
		return map.get(key);
	}

	/**
	 * Register transition to be performed when specific state and item are passed
	 * to this map.
	 * 
	 * @param state    - state to map transition to.
	 * @param item     - item to map transition to.
	 * @param newState - new state that should be set after transition is performed.
	 * @param action   - action to perform while transition is performed.
	 * @throws StateException if inner collection already contains transition for
	 *                        state and item.
	 */
	public void add(int state, Item item, int newState, TransitionAction<Item> action) throws StateException {
		Key key = new Key(state, item);
		if (map.containsKey(key))
			throw StateException.transitionAlreadyExists(state, item);
		Value value = new Value(newState, action);
		map.put(key, value);
	}

	/**
	 * Registers default transition to be performed when specific state is passed to
	 * this map. Default transitions ignore items being passed.
	 * 
	 * @param state    - state to map transition to.
	 * @param newState - new state that should be set after transition is performed.
	 * @param action   - action to perform while transition is performed.
	 * @throws StateException if inner collection already contains transition for
	 *                        state.
	 */
	public void add(int state, int newState, TransitionAction<Item> action) throws StateException {
		Key key = new Key(state, null);
		if (map.containsKey(key))
			throw StateException.transitionAlreadyExists(state);
		Value value = new Value(newState, action);
		map.put(key, value);
	}

	/**
	 * Find transition mapped to specific state and item or default transition for
	 * state, change handler's state to one specified in transition and invoke
	 * transition's action using state handler, previous state, item and new state.
	 *
	 * @param stateHandler - handler to perform transition againts.
	 * @param item         - next item.
	 * @throws StateException if inner collection misses transition for state.
	 * @throws IOException    thrown by transition action.
	 */
	public void invoke(StateHandler<Item> stateHandler, Item item) throws StateException, IOException {
		int state = stateHandler.getState();
		Value value = getOrNull(state, item);
		if (value == null) // Get default value for state.
			value = get(state);
		stateHandler.setState(value.item1);
		value.item2.invoke(state, stateHandler, item, value.item1);
	}

	/**
	 * Key of inner collection. Contains state from which transition should be
	 * performed and item required. If item is null then transition matches any
	 * item.
	 */
	class Key extends Pair<Integer, Item> {
		public Key(Integer item1, Item item2) {
			super(item1, item2);
		}
	};

	/**
	 * Value of inner collection. Contains new state transition should result in and
	 * action to be performed during transition.
	 */
	class Value extends Pair<Integer, TransitionAction<Item>> {
		public Value(Integer item1, TransitionAction<Item> item2) {
			super(item1, item2);
		}
	};
}
