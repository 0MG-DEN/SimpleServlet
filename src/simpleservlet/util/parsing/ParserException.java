package simpleservlet.util.parsing;

public class ParserException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final ParserException AMBIGIOUS_MAP;
	public static final ParserException ILLEGAL_STATE;
	public static final ParserException ILLEGAL_ITEM;
	public static final ParserException TRANSITION_NOT_FOUND;
	public static final ParserException TRANSITION_ALREADY_EXISTS;

	static {
		AMBIGIOUS_MAP = new ParserException("Ambigious map configuration");
		ILLEGAL_STATE = new ParserException("Illegal state");
		ILLEGAL_ITEM = new ParserException("Illegal item");
		TRANSITION_NOT_FOUND = new ParserException("Transition not found");
		TRANSITION_ALREADY_EXISTS = new ParserException("Transition already exists");
	}

	private ParserException(String message) {
		super(message);
	}
}
