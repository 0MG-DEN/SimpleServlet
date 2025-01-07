package simpleservlet.util;

interface Supplier<T, E extends Exception> {
	T get() throws E;
}
