package simpleservlet.util.parsing;

import java.io.*;

class Transition<Item extends Comparable<Item>> {
	public final int state;
	public final Item item;
	public final int newState;
	public final TransitionAction<Item> action;

	public Transition(int state, Item item, int newState, TransitionAction<Item> action) {
		this.state = state;
		this.item = item;
		this.newState = newState;
		this.action = action;
	}

	public boolean hasState(int state) {
		return (this.state & state) > 0;
	}

	public boolean hasItem(Item item) {
		if (this.item == null)
			return item == null;
		if (item == null)
			return false;
		return item.compareTo(this.item) == 0;
	}

	public void invoke(StateHandler stateHandler, Item item) throws ParserException, IOException {
		if (!hasState(stateHandler.getState()))
			throw ParserException.ILLEGAL_STATE;
		if (!hasItem(item) && !hasItem(null))
			throw ParserException.ILLEGAL_ITEM;
		stateHandler.removeState(state);
		stateHandler.addState(newState);
		action.invoke(state, item, newState);
	}
}
