package simpleservlet.util;

import java.nio.file.*;

import javax.servlet.*;

/**
 * Provides static methods to help with resolving paths to resources as absolute
 * or relative to application root paths.
 */
public class ResourceHelper {
	/**
	 * Get path consisting of specific parts relative to application root.
	 *
	 * @param parts - parts of path to file including folders and filename.
	 * @return Path to file relative to application root.
	 */
	public static String getRelativePath(String... parts) {
		String path;
		path = Paths.get("WEB-INF", parts).toString();
		path = "/" + path.replace('\\', '/');
		return path;
	}

	/**
	 * Get absolute path consisting of specific parts relative to application root.
	 *
	 * @param context - context in scope of which path is resolved.
	 * @param parts   - parts of path to file including folders and filename.
	 * @return Absolute path to file.
	 */
	public static String getRealPath(ServletContext context, String... parts) {
		String path;
		path = getRelativePath(parts);
		path = context.getRealPath(path);
		return path;
	}
}
