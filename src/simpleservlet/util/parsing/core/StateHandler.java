package simpleservlet.util.parsing.core;

import java.util.*;

public class StateHandler<Item> {
	private int value;
	private int flags;
	private Queue<Item> queue = new LinkedList<Item>();

	public StateHandler(int state) {
		this.value = state;
		this.flags = 0;
	}

	public int getState() {
		return value;
	}

	public boolean equals(int state) {
		return value == state;
	}

	public boolean hasState(int state) {
		return (value & state) == state;
	}

	public void setState(int state) {
		value = state;
	}

	public boolean hasFlag(int flag) {
		return (flags & flag) == flag;
	}

	public void addFlag(int flag) {
		flags |= flag;
	}

	public void removeFlag(int flag) {
		flags &= ~flag;
	}

	public void clearFlags() {
		flags = 0;
	}

	public void addItem(Item item) {
		queue.add(item);
	}

	public void clearItems() {
		queue.clear();
	}

	public Item popItem() {
		return queue.remove();
	}

	public boolean hasItems() {
		return !queue.isEmpty();
	}
}
