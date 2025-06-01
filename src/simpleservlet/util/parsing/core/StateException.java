package simpleservlet.util.parsing.core;

/**
 * Provides basic exceptions for
 * {@link simpleservlet.util.parsing.core.TransitionMap}.
 */
public class StateException extends Exception {
	/**
	 * Get {@link StateException} indicating that transition already exists for
	 * specific state and item.
	 *
	 * @param state - state resulted in error.
	 * @param item  - item resulted in error.
	 * @return {@link StateException} indicating that transition already exists.
	 */
	public static StateException transitionAlreadyExists(int state, Object item) {
		String itemText = (item == null) ? "default" : item.toString();
		return new StateException("Transition already exists for state %d and item %s", state, itemText);
	}

	/**
	 * Get {@link StateException} indicating that transition already exists for
	 * specific state.
	 *
	 * @param state - state resulted in error.
	 * @return {@link StateException} indicating that transition already exists.
	 */
	public static StateException transitionAlreadyExists(int state) {
		return new StateException("Default transition already exists for state %d", state);
	}

	/**
	 * Get {@link StateException} indicating missing transition for state and item.
	 *
	 * @param state - state resulted in error.
	 * @param item  - item resulted in error.
	 * @return {@link StateException} indicating missing transition.
	 */
	public static StateException transitionNotFound(int state, Object item) {
		String itemText = (item == null) ? "default" : item.toString();
		return new StateException("Transition not found for state %d and item %s", state, itemText);
	}

	/**
	 * Get {@link StateException} indicating missing transition for state.
	 *
	 * @param state - state resulted in error.
	 * @return {@link StateException} indicating missing transition.
	 */
	public static StateException transitionNotFound(int state) {
		return new StateException("Default transition not found for state %d", state);
	}

	/**
	 * Create new exception.
	 *
	 * @param format - message format.
	 * @param args   - arguments of message.
	 */
	private StateException(String format, Object... args) {
		super(String.format(format, args));
	}
}
