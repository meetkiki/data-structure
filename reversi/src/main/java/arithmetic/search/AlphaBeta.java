package arithmetic.search;

import arithmetic.evaluation.ReversiEvaluation;
import arithmetic.subsidiary.HistoryHeuristic;
import arithmetic.subsidiary.TranspositionTable;
import bean.BoardChess;
import bean.MinimaxResult;
import bean.Move;
import common.Constant;
import common.EntryType;
import game.GameRule;
import utils.BoardUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static common.Constant.MAX;
import static common.Constant.MIN;

/**
 * alphaBeta 算法
 *
 * @author Tao
 */
public class AlphaBeta extends SearchAlgorithm {

    private ReversiEvaluation evaluation;

    public ReversiEvaluation getEvaluation() {
        return evaluation;
    }

    public AlphaBeta(ReversiEvaluation evaluation) {
        this(evaluation, true);
    }

    /**
     * 有参构造
     * @param evaluation                估值编码
     * @param enableTranspositionTable  是否开启置换表 当搜索层数较高时较有效，但会影响内存消耗
     */
    public AlphaBeta(ReversiEvaluation evaluation, boolean enableTranspositionTable) {
        super(enableTranspositionTable);
        this.evaluation = evaluation;
    }

    @Override
    public MinimaxResult search(BoardChess boardChess, int depth) {
        this.initTranspositionTableTable();
        HistoryHeuristic.resetHistory();
        return alphaBeta(boardChess, MIN, MAX, depth);
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
    public MinimaxResult alphaBeta(BoardChess data, float alpha, float beta, int depth) {//α-β剪枝算法
        // 引入置换表
        MinimaxResult zresult;
        // 当前深度浅于历史深度 则使用 否则搜索
        if (enableTranspositionTable && (zresult = TranspositionTable.lookupTTentryByZobrist(data.getZobrist(), depth)) != null) {
            switch (zresult.getType()) {
                // 期望值
                case EXACT:
                    return zresult;
                // 下界值
                case LOWERBOUND:
                    alpha = Math.min(alpha, zresult.getMark());
                    break;
                // 上界值
                case UPPERBOUND:
                    beta = Math.min(beta, zresult.getMark());
                    break;
                default:
                    break;
            }
        }
        List<Byte> empty = data.getEmpty();
        // 如果到达预定的搜索深度 // 棋子已满
        if (depth <= Constant.start || empty.size() == Constant.EMPTY) {
            // 直接给出估值
            return currentMinimaxResult(data, depth);
        }
        LinkedList<Byte> moves = new LinkedList<>();
        if (GameRule.validMoves(data, moves) == 0) {
            // 如果对手也没有可走子
            if (data.getOppMobility() == 0) {
                // 终局 给出精确估值
                return currentMinimaxResult(data, depth);
            }
            GameRule.passMove(data);
            // 交给对手
            MinimaxResult result = alphaBeta(data, -beta, -alpha, depth).inverseMark();
            // 回退
            GameRule.unMove(data);
            return result;
        } else {
            // 历史启发式搜索 将有利的落子放在最前面 最大化alphaBeta剪枝
            HistoryHeuristic.sortMovesByHistory(moves);
            //sortMoves(moves);
            // 当前最佳估值，预设为负无穷大 己方估值为最小
            float score = MIN, value;
            boolean isFoundPV = false;
            // 最佳估值类型, EXACT为精确值, LOWERBOUND为<=alpha, UPPERBOUND为>=beta
            EntryType entryType = null;
            // 轮到已方走
            Move move = null, first = null;
            // 遍历每一种走法
            Iterator<Byte> moveIterator = moves.iterator();
            while (moveIterator.hasNext()) {
                byte curMove = moveIterator.next();
                // first move 作为搜索失败的第一个值
                if (first == null) first = BoardUtil.convertMove(curMove);
                //尝试走这步棋
                GameRule.makeMove(data, curMove);
                if (isFoundPV) {
                    // 假定找到最优剪枝 以空窗口搜索
                    value = -alphaBeta(data, -alpha - 1, -alpha, depth - 1).getMark();
                    // 如果检索失败 重新搜索
                    if (value > alpha && value < beta) {
                        // 将产生的新局面给对方
                        value = -alphaBeta(data, -beta, -alpha, depth - 1).getMark();
                    }
                } else {
                    // 将产生的新局面给对方
                    value = -alphaBeta(data, -beta, -alpha, depth - 1).getMark();
                }
                // 悔棋
                GameRule.unMove(data);
                if (value > score) {
                    score = value;
                    move = BoardUtil.convertMove(curMove);
                    if (value > alpha) {
                        entryType = EntryType.EXACT;
                        // 通过向上传递的值修正上下限
                        alpha = Math.max(value, alpha);
                        isFoundPV = true;
                    }
                }
                // 剪枝
                if (alpha >= beta) {
                    // 下限
                    entryType = EntryType.LOWERBOUND;
                    break;
                }
            }
            // 将最佳移动存入历史表
            if (move != null) HistoryHeuristic.setHistoryScore(BoardUtil.squareChess(move), depth);
            else move = first;
            // 如果搜索失败
            if (entryType == null) {
                entryType = EntryType.UPPERBOUND;
            }
            MinimaxResult result = MinimaxResult.builder().mark(score).type(entryType).move(move).depth(depth).build();
            TranspositionTable.insertZobrist(data.getZobrist(), result);
            return result;
        }
    }

    /**
     * 计算估值
     *
     * @param data
     * @param depth
     * @return
     */
    private MinimaxResult currentMinimaxResult(BoardChess data, int depth) {
        float value = evaluation.currentValue(data);
        TranspositionTable.insertZobrist(data.getZobrist(), value, depth, EntryType.EXACT);
        return MinimaxResult.builder().mark(value).depth(depth).build();
    }

    /**
     * 根据对手行动力排序
     * 插入排序
     *
     * @param moves
     */
    private LinkedList<Integer> sortMoves(LinkedList<Integer> moves) {
        // 设值方式
        // byte curMove = BoardUtil.rightShift(next,Constant.BITVALUE);
        // Move convertMove = BoardUtil.convertMove(curMove);
        if (moves.size() == 0) {
            return moves;
        }
        // 按照对手行动力从小到大排序
        Collections.sort(moves, (o1, o2) -> {
            byte mobility1 = (byte) (o1 & 0xFF);
            byte mobility2 = (byte) (o2 & 0xFF);
            return mobility1 - mobility2;
        });
        return moves;
    }
}