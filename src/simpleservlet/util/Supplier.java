package simpleservlet.util;

/**
 * Supplier that supports throwing some exception.
 *
 * @param <T> - returned object type.
 * @param <E> - exception possibly thrown by method.
 */
interface Supplier<T, E extends Exception> {
	T get() throws E;
}
