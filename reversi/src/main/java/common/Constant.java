package common;

/**
 * @author Tao
 */
public class Constant {

	/**
	 * 标识黑子
	 */
	public static final byte BLACK = -1;

	/**
	 * 标识空位
	 */
	public static final byte EMPTY = 0;

	/**
	 * 标识白子
	 */
	public static final byte WHITE = 1;

	/**
	 * 标识白可走子
	 */
	public static final byte DOT_W = 2;
	/**
	 * 标识黑可走子
	 */
	public static final byte DOT_B = -2;
	/**
	 * 边间距
	 */
	public static final int SPAN = 32;
	/**
	 * /行高
	 */
	public static final int ROW = 48;
	/**
	 * /列高
	 */
	public static final int COL = 48;

	/**
	 * 棋盘大小
	 */
	public static final int SIZE = 8;

	/**
	 * //序列帧间隔
	 */
	public static final long DELAY = 100;
	/**
	 * 动画图片
	 */
	public static final String OVERTURN = "OVERTURN%s";
}
