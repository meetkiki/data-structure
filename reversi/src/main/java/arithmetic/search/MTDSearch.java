package arithmetic.search;

import arithmetic.subsidiary.HistoryHeuristic;
import bean.BoardChess;
import bean.MinimaxResult;

import static common.Constant.MAX;
import static common.Constant.MIN;


/**
 * MTD算法
 *
 * @author Tao
 */
public class MTDSearch extends SearchAlgorithm {


    public MTDSearch(AlphaBeta alphaBeta, boolean enableTranspositionTable) {
        super(enableTranspositionTable);
        this.alphaBeta = alphaBeta;
    }

    private final AlphaBeta alphaBeta;

    @Override
    public MinimaxResult search(BoardChess data, int maxDepth) {
        this.initTranspositionTableTable();
        HistoryHeuristic.resetHistory();
        // 初始假想模型
        MinimaxResult firstguess = MinimaxResult.builder().mark(0).build();
        // 迭代深化调用MTD进行搜索
        for (int depth = 1; depth <= maxDepth; depth++) {
            firstguess = mtdf(data, firstguess, depth);
        }
        return firstguess;
    }

    /**
     * MTD搜索
     *
     * @param data
     * @param result
     * @param depth
     * @return
     */
    private MinimaxResult mtdf(BoardChess data, MinimaxResult result, int depth) {
        // 初始搜索猜想上下限界
        float upp = MAX, low = MIN, beta;
        do {
            // 在搜索中逼近上下界
            if (result.getMark() == low) {
                beta = result.getMark() + 1;
            } else {
                beta = result.getMark();
            }
            // 空窗探测
            result = alphaBeta.alphaBeta(data, beta - 1, beta, depth);
            if (result.getMark() < beta) {
                upp = result.getMark();
            } else {
                low = result.getMark();
            }
        } while (low < upp);
        return result;
    }


}
