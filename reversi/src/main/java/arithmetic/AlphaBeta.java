package arithmetic;

import bean.BoardData;
import bean.MinimaxResult;
import bean.Move;
import common.Constant;
import game.Chess;
import game.GameRule;
import utils.BoardUtil;

import java.util.Iterator;
import java.util.List;

import static common.Constant.SIZE;

/**
 * alpha_Beta 算法
 *
 * @author Tao
 */
public class AlphaBeta {


    public static int Depth = 3;
    public static int MAX = 1000000;
    public static int MIN = -1000000;
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
     * 估值函数
     *
     * @param data
     * @param player
     * @return
     */
    private static int currentValue(BoardData data, byte player) {
        int score;
        Chess[][] chess = data.getChess();
        //基于棋子分数位置的估值
        score = GameRule.player_counters(chess, player) - GameRule.player_counters(chess, player == Constant.WHITE ? Constant.BLACK : Constant.WHITE);



        return score;
    }


    private static Move best;


    public static MinimaxResult alpha_Beta(BoardData data){
        return new MinimaxResult(alpha_Beta(data,Depth),best);
    }
    /**
     * alpha_Beta 算法
     *
     * @param data
     * @param depth
     * @return
     */
    private static int alpha_Beta(BoardData data, int depth) {//α-β剪枝算法
        // 如果到达预定的搜索深度
        if (depth <= 0) {
            // 直接给出估值
            return currentValue(data, data.getNextmove());
        }
        // 轮到已方走
        if (data.getNextmove() == Constant.WHITE) {
            // 当前最佳估值，预设为负无穷大 己方估值为最小
            int best_value = MIN;
            if (GameRule.valid_moves(data, data.getNextmove()) > 0) {
                boolean[][] moves = data.getMoves();
                // 遍历每一种走法
                for(byte row=0;row<SIZE;++row){
                    for(byte col=0;col<SIZE;++col) {
                        if (moves[row][col]) {
                            BoardData temdata = BoardUtil.copyBoard(data);
                            Chess[][] chess = temdata.getChess();
                            GameRule.removeHint(temdata);
                            //尝试走这步棋
                            GameRule.make_move(chess, row, col, temdata.getNextmove(), null);
                            temdata.setNextmove(BoardUtil.change(temdata.getNextmove()));
                            GameRule.valid_moves(temdata, temdata.getNextmove());
                            // 将产生的新局面给对方
                            int value = alpha_Beta(temdata, depth - 1);
                            if (best_value < value) {
                                best_value = value;
                                if (depth == Depth)
                                    best = new Move(row, col);
                            }
                        }
                    }
                }
            }
            return best_value;
            // 轮到对方走
        } else {
            // 当前最佳估值，预设为负无穷大 己方估值为最小
            int best_value = MAX;
            if (GameRule.valid_moves(data, data.getNextmove()) > 0) {
                boolean[][] moves = data.getMoves();
                // 遍历每一种走法
                for(byte row=0;row<SIZE;++row){
                    for(byte col=0;col<SIZE;++col) {
                        if (moves[row][col]) {
                            BoardData temdata = BoardUtil.copyBoard(data);
                            Chess[][] chess = temdata.getChess();
                            GameRule.removeHint(temdata);
                            //尝试走这步棋
                            GameRule.make_move(chess, row, col, temdata.getNextmove(), null);
                            temdata.setNextmove(BoardUtil.change(temdata.getNextmove()));
                            GameRule.valid_moves(temdata, temdata.getNextmove());
                            // 将产生的新局面给对方
                            int value = alpha_Beta(temdata, depth - 1);
                            if (best_value > value) {
                                best_value = value;
                                if (depth == Depth)
                                    best = new Move(row, col);
                            }
                        }
                    }
                }
            }
            return best_value;
        }

    }

}