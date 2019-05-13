package arithmetic;

import bean.BoardChess;
import bean.ChessStep;
import bean.MinimaxResult;
import bean.Move;
import common.Bag;
import common.Constant;
import game.GameRule;
import utils.BoardUtil;

import java.util.Iterator;

/**
 * alphaBeta 算法
 *
 * @author Tao
 */
public class AlphaBeta {


    public static int Depth = 8;
    public static int MAX = 1000000000;
    public static int MIN = -1000000000;

    public static MinimaxResult alphaBeta(BoardChess data){
        MinimaxResult result = alphaBeta(data, MIN, MAX, Depth);
        return result;
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
    private static MinimaxResult alphaBeta(BoardChess data, int alpha, int beta, int depth) {//α-β剪枝算法
        // 如果到达预定的搜索深度
        if (depth <= 0) {
            // 直接给出估值
            return MinimaxResult.builder().mark(ReversiEvaluation.currentValue(data)).depth(depth).build();
        }
        Bag<Byte> moves = new Bag<>();
        Bag<Byte> empty = data.getEmpty();
        // 棋子已满
        if (empty.size() == Constant.EMPTY){
            return MinimaxResult.builder().mark(beta).depth(depth).build().inverseMark();
        }
        GameRule.valid_moves(data,moves);
        if (moves.isEmpty()) {
            // 跳过
            GameRule.passMove(data);
            if (GameRule.valid_moves(data) == 0){
                // 终局
                return MinimaxResult.builder().mark(beta).depth(depth).build().inverseMark();
            }
            return alphaBeta(data, -beta, -alpha, depth - 1).inverseMark();
        }
        // 轮到已方走
        Move move = null;
        // 当前最佳估值，预设为负无穷大 己方估值为最小
        int best_value = Integer.MIN_VALUE;
        // 遍历每一种走法
        Iterator<Byte> moveIterator = moves.iterator();
        while (moveIterator.hasNext()){
            Byte curMove = moveIterator.next();
            //尝试走这步棋
            GameRule.make_move(data, curMove);
            // 将产生的新局面给对方
            int value = -alphaBeta(data, -beta , -alpha, depth - 1).getMark();
            // 悔棋
            GameRule.un_move(data);
            if (value > best_value){
                move = BoardUtil.convertMove(curMove);
                // 通过向上传递的值修正下限
                if (value > alpha) {
                    alpha = value;
                    // 当向上传递的值大于上限时 剪枝
                    if (value >= beta){
                        return MinimaxResult.builder().mark(value).move(move).depth(depth).build();
                    }
                }
            }
        }
        return MinimaxResult.builder().mark(alpha).move(move).depth(depth).build();
    }

}