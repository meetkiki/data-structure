package arithmetic;

import bean.BoardData;
import bean.MinimaxResult;
import bean.Move;
import common.Constant;
import game.Chess;
import game.GameRule;
import utils.BoardUtil;

import static common.Constant.SIZE;

/**
 * alphaBeta 算法
 *
 * @author Tao
 */
public class AlphaBeta {


    public static int Depth = 5;
    public static int MAX = 1000000;
    public static int MIN = -1000000;



    /**
     * 估值函数
     *
     * @param data
     * @param player
     * @return
     */
    private static int currentValue(BoardData data, byte player) {
        int score = 0;
        byte other = player == Constant.WHITE ? Constant.BLACK : Constant.WHITE;
        Chess[][] chess = data.getChess();
        // 基于棋子数目的估值
        score += Evaluation.player_counters(chess, player) - Evaluation.player_counters(chess, other);
        // 基于棋子分数位置的估值
        score += Evaluation.evaluation(chess, player) - Evaluation.evaluation(chess, other);
        return score;
    }


    public static MinimaxResult alphaBeta(BoardData data){
        return alphaBeta(data,MIN,MAX,Depth);
    }
    /**
     * alphaBeta 算法
     *
     * @param data
     * @param depth 搜索深度
     * @param alpha 下限
     * @param beta  上限
     * @return
     */
    private static MinimaxResult alphaBeta(BoardData data, int alpha, int beta, int depth) {//α-β剪枝算法
        // 如果到达预定的搜索深度
        if (depth <= 0) {
            // 直接给出估值
            return MinimaxResult.builder().mark(currentValue(data, data.getNextmove())).build();
        }
        // 轮到已方走
        Move move = null;
        // 当前最佳估值，预设为负无穷大 己方估值为最小
        if (GameRule.valid_moves(data, data.getNextmove()) > 0) {
            boolean[][] moves = data.getMoves();
            // 遍历每一种走法
            for(byte row=0;row<SIZE;++row){
                for(byte col=0;col<SIZE;++col) {
                    if (moves[row][col]) {
                        // 创建模拟棋盘
                        Move curM = Move.builder().row(row).col(col).build();
                        BoardData temdata = BoardUtil.copyBoard(data);
                        Chess[][] chess = temdata.getChess();
                        GameRule.removeHint(temdata);
                        //尝试走这步棋
                        GameRule.make_move(chess, curM, temdata.getNextmove(), true);
                        temdata.setNextmove(BoardUtil.change(temdata.getNextmove()));
                        GameRule.valid_moves(temdata, temdata.getNextmove());
                        // 将产生的新局面给对方
                        int value = -alphaBeta(temdata, -beta , -alpha, depth - 1).getMark();
                        // 剪枝
                        if (value >= beta){
                            return MinimaxResult.builder().mark(beta).move(move).build();
                        }
                        // 通过向上传递的值修正上下限
                        if (value > alpha) {
                            alpha = value;
                            move = curM;
                        }
                    }
                }
            }
        } else {
            // 没有可走子 交给对方
            data.setNextmove(BoardUtil.change(data.getNextmove()));
            if (GameRule.valid_moves(data, data.getNextmove()) > 0){
                return alphaBeta(data, -beta, -alpha, depth - 1);
            }else{
                return MinimaxResult.builder().mark(currentValue(data, data.getNextmove())).build();
            }
        }
        return MinimaxResult.builder().mark(alpha).move(move).build();
    }

}