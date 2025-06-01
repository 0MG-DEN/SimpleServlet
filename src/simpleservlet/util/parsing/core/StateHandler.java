package simpleservlet.util.parsing.core;

import java.util.*;

/**
 * Provides container that can hold current state (as integer), additional set
 * of flags (as integer) and queue of items. Basically used while traversing
 * {@link simpleservlet.util.parsing.core.TransitionMap}.
 *
 * @param <Item> - objects to hold in processing queue.
 */
public class StateHandler<Item> {
	private int value;
	private int flags;
	private Queue<Item> queue = new LinkedList<Item>();

	/**
	 * Create new handler with inital state. Flags are set to 0.
	 *
	 * @param state - initial state.
	 */
	public StateHandler(int state) {
		this.value = state;
		this.flags = 0;
	}

	/**
	 * Get current state.
	 *
	 * @return Current state.
	 */
	public int getState() {
		return value;
	}

	/**
	 * Check whether current state matches input.
	 *
	 * @param state - state to compare against.
	 * @return Flag indicating whether current state matches input.
	 */
	public boolean equals(int state) {
		return value == state;
	}

	/**
	 * Check whether current state has input state, i.e. bitwise overlaps.
	 *
	 * @param state - state to compare against.
	 * @return Flag indicating whether current state bits fully overlap input.
	 */
	public boolean hasState(int state) {
		return (value & state) == state;
	}

	/**
	 * Set current state to specific value.
	 *
	 * @param state - state to set.
	 */
	public void setState(int state) {
		value = state;
	}

	/**
	 * Check whether current state has input state, i.e. bitwise overlaps.
	 *
	 * @param flag - flag to compare against.
	 * @return Flag indicating whether current flags bits fully overlap input.
	 */
	public boolean hasFlag(int flag) {
		return (flags & flag) == flag;
	}

	/**
	 * Add flags to current flags, i.e. bitwise OR.
	 *
	 * @param flag - bits to add to current flags.
	 */
	public void addFlag(int flag) {
		flags |= flag;
	}

	/**
	 * Remove flags from current flags, i.e. bitwise AND with negated input.
	 *
	 * @param flag - bits to remove from current flags.
	 */
	public void removeFlag(int flag) {
		flags &= ~flag;
	}

	/**
	 * Clear current flags. Set to 0.
	 */
	public void clearFlags() {
		flags = 0;
	}

	/**
	 * Push item to inner processing queue.
	 *
	 * @param item - item to push to inner processing queue.
	 */
	public void addItem(Item item) {
		queue.add(item);
	}

	/**
	 * Clear inner processing queue.
	 */
	public void clearItems() {
		queue.clear();
	}

	/**
	 * Remove and return the top item from inner processing queue.
	 *
	 * @return Item removed from the top of inner processing queue.
	 */
	public Item popItem() {
		return queue.remove();
	}

	/**
	 * Check if inner processing queue has any items.
	 *
	 * @return Flag indicating whether inner processing queue has any items.
	 */
	public boolean hasItems() {
		return !queue.isEmpty();
	}
}
