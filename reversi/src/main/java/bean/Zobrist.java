package bean;

import java.util.Random;

import static common.Constant.SIZE;

/**
 * @author ypt
 * @ClassName Zobrist
 * @Description Zobrist哈希
 * @date 2019/5/13 17:10
 */
public final class Zobrist {

    public static final Long[][][] zobrist;

    static {
        Random random = new Random();
        zobrist = new Long[SIZE][][];
        for (int row = 0; row < SIZE; row++) {
            zobrist[row] = new Long[SIZE][];
            for (int col = 0; col < SIZE; col++) {
                zobrist[row][col] = new Long[2];
                zobrist[row][col][0] = random.nextLong();
                zobrist[row][col][1] = random.nextLong();
            }
        }
    }

    public static Long[][][] getZobrist() {
        return zobrist;
    }

    /**
     * Zobrist哈希值
     * @param row
     * @param col
     * @param player
     * @return
     */
    public static Long getZobrist(int row,int col,int player) {
        return zobrist[row][col][player];
    }

}
