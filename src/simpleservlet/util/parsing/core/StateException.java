package simpleservlet.util.parsing.core;

public class StateException extends Exception {
	public static StateException transitionAlreadyExists(int state, Object item) {
		String itemText = (item == null) ? "default" : item.toString();
		return new StateException("Transition already exists for state %d and item %s", state, itemText);
	}

	public static StateException transitionAlreadyExists(int state) {
		return new StateException("Default transition already exists for state %d", state);
	}

	public static StateException transitionNotFound(int state, Object item) {
		String itemText = (item == null) ? "default" : item.toString();
		return new StateException("Transition not found for state %d and item %s", state, itemText);
	}

	public static StateException transitionNotFound(int state) {
		return new StateException("Default transition not found for state %d", state);
	}

	private StateException(String format, Object... args) {
		super(String.format(format, args));
	}
}
