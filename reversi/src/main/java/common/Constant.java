package common;


import bean.WeightIndividual;

/**
 * @author Tao
 */
public class Constant {

	/**
	 * 标识黑子
	 */
	public static final byte BLACK = 2;

	/**
	 * 标识白子
	 */
	public static final byte WHITE = 1;

	/**
	 * 标识空位 或者0
	 */
	public static final byte EMPTY = 0;

	/**
	 * 标识白可走子
	 */
	public static final byte DOT_W = 3;
	/**
	 * 标识黑可走子
	 */
	public static final byte DOT_B = 4;
	/**
	 * 边界
	 */
	public static final byte BOUNDARY = 5;
	/**
	 * 玩家种类
	 */
	public static final byte PLAYERTYPE = 3;

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
	public static final int DELAY = 60;

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
	 * 默认搜索深度
	 */
    public static final int DEFAULTDEPTH = 8;
	/**
	 * 终局搜索深度
	 */
	public static final int OUTDEPTH = 16;
	/**
	 * 开局 空闲位置40以上
	 */
	public static final int OPENING = 40;
	/**
	 * 中盘 空闲位置18 - 40
	 */
	public static final int MIDDLE = 18;
	/**
     * 搜索的极大极小值
     */
    public static int MAX = 600000;
    public static int MIN = -600000;

	/**
	 * Warren Smith 棋盘模型长度
	 */
	public static final byte MODEL = 91;

	public static final byte DIRALL = 8;

	public static final byte ALLSTEP = 64;
	/**
	 * byte位为8
	 */
	public static final byte BITVALUE = 8;

	public static final WeightIndividual NULL = null;

	public static int start = 0;

	/**
	 * 基因序列长度
	 */
	public static final byte GENELENGTH = 21;
	/**
	 * 基因最大数值
	 */
	public static final int GENEMAX = 256;
	/**
	 * 收敛分数
	 */
	public static final double convergence = 5;
}
