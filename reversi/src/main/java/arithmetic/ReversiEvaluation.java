package arithmetic;

import bean.BoardData;
import common.Constant;
import game.Chess;

import static common.Constant.SIZE;

/**
 * @author ypt
 * @ClassName ReversiEvaluation
 * @Description 估值方法
 * @date 2019/5/8 13:58
 */
public class ReversiEvaluation {

    private static final int val = 5;

    /**
     * 估值函数
     *
     * @param data
     * @param player
     * @return
     */
    public static int currentValue(BoardData data, byte player) {
        int score = 0;
        byte other = player == Constant.WHITE ? Constant.BLACK : Constant.WHITE;
        Chess[][] chess = data.getChess();
        // 基于棋子数目的估值
        score += val * (ReversiEvaluation.player_counters(chess, player) - ReversiEvaluation.player_counters(chess, other));
        // 基于棋子分数位置的估值
        score += ReversiEvaluation.evaluation(chess, player) - ReversiEvaluation.evaluation(chess, other);
        return score;
    }

    /**
     * //每一个棋子的权重
     */
    private final static int[][] evaluation = {
            {90, -60, 10, 10, 10, 10, -60, 90},
            {-60, -80, 5, 5, 5, 5, -80, -60},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {-60, -80, 5, 5, 5, 5, -80, -60},
            {90, -60, 10, 10, 10, 10, -60, 90}};

    /**
     * 位置估值
     * @param chess
     * @param player
     * @return
     */
    private static int evaluation(Chess[][] chess, byte player){
        int count = 0,row,col;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                if(chess[row][col].getChess() == player)
                    count += evaluation[row][col];
        return count;
    }


    /**
     * /棋子统计方法
     */
    public static int player_counters(Chess[][] chess, byte player){
        int count = 0,row,col;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                if(chess[row][col].getChess() == player)
                    ++count;
        return count;
    }

}
