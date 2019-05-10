package arithmetic;

import bean.BoardChess;
import common.Constant;

import static common.Constant.SIZE;

/**
 * @author ypt
 * @ClassName ReversiEvaluation
 * @Description 估值方法
 * @date 2019/5/8 13:58
 */
public class ReversiEvaluation {

    private static final int val = 2;

    /**
     * 估值函数
     *
     * @param data
     * @param player
     * @return
     */
    public static int currentValue(BoardChess data, byte player) {
        int score = 0;
        byte other = player == Constant.WHITE ? Constant.BLACK : Constant.WHITE;
        byte[][] chess = data.getChess();
        // 基于棋子数目的估值
        //score += val * (ReversiEvaluation.player_counters(chess, player) - ReversiEvaluation.player_counters(chess, other));
        // 基于棋子分数位置的估值
        score += ReversiEvaluation.evaluation(chess, player) - ReversiEvaluation.evaluation(chess, other);
        return score;
    }

    /**
     * //每一个棋子的权重
     */
    private final static int[][] evaluation = {
            {100,  -5,  10,   5,   5,  10,  -5, 100},
            {-5, -45,   1,   1,   1,   1, -45,  -5},
            {10,   1,   3,   2,   2,   3,   1,  10},
            {5,   1,   2,   1,   1,   2,   1,   5},
            {5,   1,   2,   1,   1,   2,   1,   5},
            {10,   1,   3,   2,   2,   3,   1,  10},
            {-5, -45,   1,   1,   1,   1, -45,  -5},
            {100,  -5,  10,   5,   5,  10,  -5, 100}};

    /**
     * 位置估值
     * @param chess
     * @param player
     * @return
     */
    private static int evaluation(byte[][] chess, byte player){
        int count = 0,row,col;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                if(chess[row][col] == player)
                    count += evaluation[row][col];
        return count;
    }


    /**
     * /棋子统计方法
     */
    public static int player_counters(byte[][] chess, byte player){
        int count = 0,row,col;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                if(chess[row][col] == player)
                    ++count;
        return count;
    }

}
