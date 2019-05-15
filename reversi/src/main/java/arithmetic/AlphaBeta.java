package arithmetic;

import bean.BoardChess;
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
        Bag<Byte> empty = data.getEmpty();
        // 如果到达预定的搜索深度 // 棋子已满
        if (depth <= 0 || empty.size() == Constant.EMPTY) {
            // 直接给出估值
            return MinimaxResult.builder().mark(ReversiEvaluation.currentValue(data)).depth(depth).build();
        }
        Bag<Byte> moves = new Bag<>();
        GameRule.valid_moves(data,moves);
        if (moves.isEmpty()) {
            // 当前选择无期可走跳过
            data.setCurrMove(BoardUtil.change(data.getCurrMove()));
            if (GameRule.valid_moves(data) == Constant.EMPTY){
                // 终局 会转换角色
                return MinimaxResult.builder().mark(ReversiEvaluation.endValue(data)).depth(depth).build();
            }
            // 交给对手
            return alphaBeta(data, -beta, -alpha, depth).inverseMark();
        }
        // 轮到已方走
        Move move = null;
        // 当前最佳估值，预设为负无穷大 己方估值为最小
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
            // 通过向上传递的值修正下限
            if (value > alpha) {
                move = BoardUtil.convertMove(curMove);
                alpha = value;
                // 当向上传递的值大于上限时 剪枝
                if (value >= beta){
                    return MinimaxResult.builder().mark(value).move(move).depth(depth).build();
                }
            }
        }
        return MinimaxResult.builder().mark(alpha).move(move).depth(depth).build();
    }

}