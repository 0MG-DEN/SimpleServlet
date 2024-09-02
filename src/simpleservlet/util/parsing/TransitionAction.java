package simpleservlet.util.parsing;

import java.io.*;

interface TransitionAction<Item> {
	void invoke(int state, Item item, int newState) throws IOException;
}
