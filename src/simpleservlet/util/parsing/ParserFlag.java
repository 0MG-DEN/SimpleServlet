package simpleservlet.util.parsing;

/**
 * Flags used by {@link simpleservlet.util.parsing.Parser}. Values are mapped
 * as follows: <code><ul>
 * <li>[0x0010] TAG_IMG</li>
 * <li>[0x0020] TAG_IMG_1</li>
 *
 * <li>[0x0040] TAG_VIDEO</li>
 * <li>[0x0080] TAG_VIDEO_1</li>
 * </ul></code>
 */
class ParserFlag {
	public static final int TAG_IMG = 0x0010;
	public static final int TAG_IMG_1 = 0x0020;
	public static final int TAG_VIDEO = 0x0040;
	public static final int TAG_VIDEO_1 = 0x0080;
}
