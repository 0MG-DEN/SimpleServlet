package simpleservlet.util.parsing;

/**
 * States used by {@link simpleservlet.util.parsing.Parser}. Values are mapped
 * as follows: <code><ul>
 * <li>[0x0001] NONE</li>
 * <li>[0x0002] TAG_START</li>
 * <li>[0x0004] TAG_INNER</li>
 * <li>[0x0008] TAG_CLOSE</li>
 * 
 * <li>[0x0010] TAG_IMG</li>
 * <li>[0x0020] TAG_IMG_I</li>
 * <li>[0x0040] TAG_IMG_M</li>
 * <li>[0x0080] TAG_IMG_G</li>
 * 
 * <li>[0x0100] TAG_IMG_1</li>
 * <li>[0x0200] TAG_VIDEO</li>
 * <li>[0x0400] TAG_VIDEO_V</li>
 * <li>[0x0800] TAG_VIDEO_I</li>
 * 
 * <li>[0x1000] TAG_VIDEO_D</li>
 * <li>[0x2000] TAG_VIDEO_E</li>
 * <li>[0x4000] TAG_VIDEO_O</li>
 * <li>[0x8000] TAG_VIDEO_1</li>
 * </ul></code>
 */
class State {
	public static final int NONE = 0x0001;
	public static final int TAG_START = 0x0002;
	public static final int TAG_INNER = 0x0004;
	public static final int TAG_CLOSE = 0x0008;

	public static final int TAG_IMG = 0x0010;
	public static final int TAG_IMG_I = 0x0020;
	public static final int TAG_IMG_M = 0x0040;
	public static final int TAG_IMG_G = 0x0080;

	public static final int TAG_IMG_1 = 0x0100;
	public static final int TAG_VIDEO = 0x0200;
	public static final int TAG_VIDEO_V = 0x0400;
	public static final int TAG_VIDEO_I = 0x0800;

	public static final int TAG_VIDEO_D = 0x1000;
	public static final int TAG_VIDEO_E = 0x2000;
	public static final int TAG_VIDEO_O = 0x4000;
	public static final int TAG_VIDEO_1 = 0x8000;
}
