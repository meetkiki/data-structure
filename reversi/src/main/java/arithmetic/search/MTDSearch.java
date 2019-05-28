package arithmetic.search;

import arithmetic.SearchAlgorithm;
import arithmetic.evaluation.ReversiEvaluation;
import arithmetic.search.AlphaBeta;
import arithmetic.subsidiary.HistoryHeuristic;
import arithmetic.subsidiary.TranspositionTable;
import bean.BoardChess;
import bean.MinimaxResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static common.Constant.MAX;
import static common.Constant.MIN;


/**
 * MTD算法
 * @author Tao
 */
public class MTDSearch implements SearchAlgorithm {

    private static final int core = Runtime.getRuntime().availableProcessors();

    private static final ExecutorService executorService = new ThreadPoolExecutor(core, core, 0l, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
        private AtomicInteger max = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread("mtd pool - " + max.incrementAndGet() + " - thread");
        }
    });

    @Override
    public MinimaxResult search(BoardChess data, int maxDepth) {
        ReversiEvaluation.setCount(0);
        TranspositionTable.resetZobrist();
        HistoryHeuristic.resetHistory();
        // 初始假想模型
        MinimaxResult firstguess = MinimaxResult.builder().mark(0).build();
        // 迭代深化调用MTD进行搜索
        for (int depth = 1; depth <= maxDepth; depth++) {
            firstguess = mtdf(data,firstguess,depth);
        }
        return firstguess;
    }

    /**
     * MTD搜索
     * @param data
     * @param result
     * @param depth
     * @return
     */
    private MinimaxResult mtdf(BoardChess data, MinimaxResult result, int depth) {
        // 初始搜索猜想上下限界
        int upp = MAX,low = MIN,beta;
        do {
            // 在搜索中逼近上下界
            if (result.getMark() == low){
                beta = result.getMark() + 10;
            }else {
                beta = result.getMark();
            }
            // 空窗探测
            result = AlphaBeta.alphaBeta(data,beta - 1,beta,depth);
            if (result.getMark() < beta){
                upp = result.getMark();
            }else {
                low = result.getMark();
            }
        } while (low < upp);
        return result;
    }


}
