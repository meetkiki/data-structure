package utils;

import bean.Move;
import common.Constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static common.Constant.SIZE;

/**
 * 全局缓存类
 *  空间换时间
 */
public class CacheContext {

    /**
     * Warren Smith 棋盘方向   右 左 左下 右上 下 上 右下 左上
     */
    public static final byte[] dirInc = {1,-1,8,-8,9,-9,10,-10};
    /**
     * 初始棋子
     */
    public static final byte[][] initChess = {
            {40,Constant.WHITE},{41,Constant.BLACK},
            {49,Constant.BLACK},{50,Constant.WHITE}
    };
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
     * 移动位置数组 上索引对应 通过cell取move
     */
    public static final byte[] movesIndex = new byte[Constant.MODEL];
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
        return stabistor[0] == cell || stabistor[1] == cell || stabistor[2] == cell || stabistor[3] == cell;
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

        // movesIndex建立
        for (byte cell = 0; cell < Constant.MODEL; cell++) {
            for (byte index = 0; index < moves.length; index++) {
                if (moves[index] == cell){
                    movesIndex[cell] =  index;
                }
            }
        }
    }
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
