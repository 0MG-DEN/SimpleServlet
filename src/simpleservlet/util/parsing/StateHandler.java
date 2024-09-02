package simpleservlet.util.parsing;

class StateHandler {
	private int value;

	public StateHandler(int state) {
		this.value = state;
	}

	public int getState() {
		return value;
	}

	public boolean equals(int state) {
		return value == state;
	}

	public boolean hasState(int state) {
		return (value & state) > 0;
	}

	public void addState(int state) {
		value |= state;
	}

	public void removeState(int state) {
		value &= ~state;
	}
}
