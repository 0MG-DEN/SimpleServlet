package simpleservlet.util;

import java.nio.file.*;

import javax.servlet.*;

public class ResourceHelper {
	public static String getRelativePath(String... parts) {
		String path;
		path = Paths.get("WEB-INF", parts).toString();
		path = "/" + path.replace('\\', '/');
		return path;
	}

	public static String getRealPath(ServletContext context, String... parts) {
		String path;
		path = getRelativePath(parts);
		path = context.getRealPath(path);
		return path;
	}
}
