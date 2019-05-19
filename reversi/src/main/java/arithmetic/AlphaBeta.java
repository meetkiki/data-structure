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
        try {
            ReversiEvaluation.setCount(0);
            if (GameRule.valid_moves(data) == 0){
                return MinimaxResult.builder().mark(MIN).build();
            }
            return alphaBeta(data, MIN, MAX, Depth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        Bag<Integer> moves = new Bag<>();
        GameRule.valid_moves(data,moves);
        // 启发式搜索 将不利的落子放在最后 最大化alphaBeta剪枝
        sortMoves(moves);
        // 轮到已方走
        Move move = null;
        // 当前最佳估值，预设为负无穷大 己方估值为最小
        int best_value = MIN;
        // 遍历每一种走法
        Iterator<Integer> moveIterator = moves.iterator();
        while (moveIterator.hasNext()){
            Integer next = moveIterator.next();
            byte curMove = BoardUtil.rightShift(next,Constant.BITVALUE);
            Move convertMove = BoardUtil.convertMove(curMove);
            //尝试走这步棋
            GameRule.make_move(data, curMove);
            // 将产生的新局面给对方
            int value = -alphaBeta(data, -beta , -alpha, depth - 1).getMark();
            // 悔棋
            GameRule.un_move(data);
            if (value > best_value){
                move = BoardUtil.convertMove(curMove);
                best_value = value;
            }
            // 当向上传递的值大于上限时 剪枝
            if (value >= beta){
                return MinimaxResult.builder().mark(value).move(move).depth(depth).build();
            }
            // 通过向上传递的值修正上下限
            alpha = Math.max(value,alpha);
        }
        // 如果没有合法的步数
        if (best_value == MIN) {
            // 如果对手也没有可走子
            if (data.getOtherMobility() == 0){
                // 终局 给出精确估值
                return MinimaxResult.builder().mark(ReversiEvaluation.endValue(data)).depth(depth).build();
            }
            data.setCurrMove(BoardUtil.change(data.getCurrMove()));
            // 交给对手
            best_value = alphaBeta(data, -beta, -alpha, depth).inverseMark().getMark();
            // 回退
            data.setCurrMove(BoardUtil.change(data.getCurrMove()));
        }
        return MinimaxResult.builder().mark(best_value).move(move).depth(depth).build();
    }

    /**
     * 根据对手行动力排序
     *  插入排序
     * @param moves
     */
    private static Bag<Integer> sortMoves(Bag<Integer> moves) {
        if (moves.size() == 0){
            return moves;
        }
        // 按照行动力从小到大排序
        moves.sort(moves,((o1, o2) -> {
            byte mobility1 = (byte) (o1 & 255);
            byte mobility2 = (byte) (o2 & 255);
            return mobility1 - mobility2;
        }));
        checkIsSorted(moves);
        return moves;
    }

    private static void checkIsSorted(Bag<Integer> moves) {
        Integer last = null;
        Iterator<Integer> it = moves.iterator();
        while (it.hasNext()){
            Integer curr = it.next();
            if (last == null){
                last = curr;
                continue;
            }
            byte cubit = (byte) (curr & 255);
            byte labit = (byte) (last & 255);
            if (cubit < labit){
                throw new IllegalArgumentException("排序未完成!");
            }
            last = curr;
        }
    }

}