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
	/**
	 * 动画处理开始
	 */
	public static final String START = "0";
	/**
	 * 动画处理结束
	 */
	public static final String END = "1";

	/**
	 * Warren Smith 棋盘模型长度
	 */
	public static final byte MODEL = 91;

	public static final byte DIRALL = 8;

	/**
	 * Warren Smith 棋盘方向   右 左 左下 右上 下 上 右下 左上
	 */
	public static final byte[] dirInc = {1,-1,8,-8,9,-9,10,-10};
	/**
	 * 搜索位置的对应棋盘的掩码
	 *  对应二进制的比特位
	 *	1表示需要搜索 0表示不需要搜索
	 */
	public static final short[] dirMask = {
			0,0,0,0,0,0,0,0,0,
			0,81,81,87,87,87,87,22,22,
			0,81,81,87,87,87,87,22,22,
			0,121,121,255,255,255,255,182,182,
			0,121,121,255,255,255,255,182,182,
			0,121,121,255,255,255,255,182,182,
			0,121,121,255,255,255,255,182,182,
			0,41,41,171,171,171,171,162,162,
			0,41,41,171,171,171,171,162,162,
			0,0,0,0,0,0,0,0,0,0
	};
	/**
	 * 边界
	 */
	public static final byte BOUNDARY = 4;
}
