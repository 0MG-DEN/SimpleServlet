package simpleservlet.util.parsing.core;

import java.io.*;

/**
 * Action to perform when state changes.
 *
 * @param <Item> - objects held in processing queue of state handler.
 */
public interface TransitionAction<Item> {
	void invoke(int state, StateHandler<Item> stateHandler, Item item, int newState) throws IOException;
}
