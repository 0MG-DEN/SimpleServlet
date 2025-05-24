package simpleservlet.util.parsing.core;

import java.io.*;

public interface TransitionAction<Item> {
	void invoke(int state, StateHandler<Item> stateHandler, Item item, int newState) throws IOException;
}
