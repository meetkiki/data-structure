package common;

import bean.Move;
import game.Board;
import utils.BoardUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tao
 */
public class Constant {

	/**
	 * 标识黑子
	 */
	public static final byte BLACK = -1;

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
	 * Warren Smith 棋盘模型长度
	 */
	public static final byte MODEL = 91;

	public static final byte DIRALL = 8;

	/**
	 * byte位为8
	 */
	public static final byte BITVALUE = 8;

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
			0,	0,	0,	0,	0,	0,	0,	0,	0,
			0,	81,	81,	87,	87,	87,	87,	22,	22,
			0,	81,	81,	87,	87,	87,	87,	22,	22,
			0,	121,121,255,255,255,255,182,182,
			0,	121,121,255,255,255,255,182,182,
			0,	121,121,255,255,255,255,182,182,
			0,	121,121,255,255,255,255,182,182,
			0,	41,	41,	171,171,171,171,162,162,
			0,	41,	41,	171,171,171,171,162,162,
			0,	0,	0,	0,	0,	0,	0,	0,	0,	0
	};
	/**
	 * 移动位置数组 与Warren Smith 棋盘对应
	 */
	public static final byte[][] moveConstant = {
			{10,11,12,13,14,15,16,17},
			{19,20,21,22,23,24,25,26},
			{28,29,30,31,32,33,34,35},
			{37,38,39,40,41,42,43,44},
			{46,47,48,49,50,51,52,53},
			{55,56,57,58,59,60,61,62},
			{64,65,66,67,68,69,70,71},
			{73,74,75,76,77,78,79,80}
	};

	/**
	 * 移动位置数组 与Warren Smith 棋盘对应
	 */
	public static final byte[] moves = {
			10,11,12,13,14,15,16,17,
			19,20,21,22,23,24,25,26,
			28,29,30,31,32,33,34,35,
			37,38,39,40,41,42,43,44,
			46,47,48,49,50,51,52,53,
			55,56,57,58,59,60,61,62,
			64,65,66,67,68,69,70,71,
			73,74,75,76,77,78,79,80
	};
	/**
	 * 初始稳定子
	 * 	四个角
	 */
	public static final byte[] stabistor = {10,17,73,80};

	/**
	 * 是否是绝对稳定子
	 * @param cell
	 * @return
	 */
	public static boolean includeStabistor(byte cell){
		for (int i = 0; i < stabistor.length; i++) {
			if (cell == stabistor[i]){
				return true;
			}
		}
		return false;
	}
	/**
	 * 移动位置反数组 与标准 棋盘对应
	 */
	public static final Map<Byte, Move> mapMove;

	/**
	 * 缓存位置数组
	 */
	static {
		Map<Byte, Move> byteMoveMap = new HashMap<>();
		for (byte row = 0; row < SIZE; row++) {
			for (byte col = 0; col < SIZE; col++) {
				byte bmove = moveConstant[row][col];
				Move move = Move.builder().row(row).col(col).build();
				byteMoveMap.put(bmove,move);
			}
		}
		mapMove = Collections.unmodifiableMap(byteMoveMap);
	}
	/**
	 * 边界
	 */
	public static final byte BOUNDARY = 4;
	/**
	 * 玩家种类
	 */
	public static final byte PLAYERTYPE = 3;

	/**
	 * 标准坐标系
	 */
	public static final String[][] coordinateStr = {
			{"A1","B1","C1","D1","E1","F1","G1","H1"},
			{"A2","B2","C2","D2","E2","F2","G2","H2"},
			{"A3","B3","C3","D3","E3","F3","G3","H3"},
			{"A4","B4","C4","D4","E4","F4","G4","H4"},
			{"A5","B5","C5","D5","E5","F5","G5","H5"},
			{"A6","B6","C6","D6","E6","F6","G6","H6"},
			{"A7","B7","C7","D7","E7","F7","G7","H7"},
			{"A8","B8","C8","D8","E8","F8","G8","H8"},
	};
}
