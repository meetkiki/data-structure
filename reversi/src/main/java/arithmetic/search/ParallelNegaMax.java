package arithmetic.search;

import arithmetic.evaluation.ReversiEvaluation;
import bean.BoardChess;
import bean.MinimaxResult;
import bean.Move;
import bean.WeightIndividual;
import game.GameRule;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static common.Constant.MIN;
import static common.Constant.SIZE;

/**
 * @author ypt
 * @ClassName ParallelNegaMax
 * @Description 并行算法
 * 因为Alpha-beta的剪枝依赖平级计算结果 如果这里使用并行算法则无法同时启动平级搜索 故效率提升效果不明显
 * @date 2019/5/8 20:35
 */
public class ParallelNegaMax extends SearchAlgorithm {
    /**
     * 抽象搜索
     *
     * @param boardChess
     * @param depth
     * @return
     */
    @Override
    public MinimaxResult search(BoardChess boardChess, int depth) {
        NegaMaxRun run = new NegaMaxRun(boardChess, depth);
        try {
            return pool.submit(run).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("parallelNegaMax error.", e);
        }
    }

    private static final ReversiEvaluation evaluation = new ReversiEvaluation(WeightIndividual.DEFAULT);

    private static final ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() << 1);

    /**
     * 异步执行计算线程
     */
    static class NegaMaxRun extends RecursiveTask<MinimaxResult> {
        private BoardChess data;
        private int depth;

        public NegaMaxRun(BoardChess data, int depth) {
            this.data = data;
            this.depth = depth;
        }

        @Override
        protected MinimaxResult compute() {
            // 如果到达预定的搜索深度
            if (depth <= 0) {
                // 直接给出估值
                return MinimaxResult.builder().mark(evaluation.currentValue(data)).build();
            }
            // 轮到已方走
            Move move = null;
            // 当前最佳估值，预设为负无穷大 己方估值为最小
            float best_value = MIN;
            boolean[][] moves = new boolean[SIZE][SIZE];
            if (GameRule.validMoves(data, moves) > 0) {
                List<Move> nextmoves = new ArrayList<>();
                // 遍历每一种走法
                for (byte row = 0; row < SIZE; ++row)
                    for (byte col = 0; col < SIZE; ++col)
                        if (moves[row][col]) nextmoves.add(new Move(row, col));
                List<NegaMaxRun> list = new ArrayList<>();
                Map<NegaMaxRun, Move> betaRunMoveMap = new HashMap<>();
                for (Move curM : nextmoves) {
                    // 创建模拟棋盘
                    BoardChess temdata = null; //BoardUtil.cloneChess(data);
                    //尝试走这步棋
                    GameRule.makeMove(temdata, BoardUtil.squareChess(curM));
                    temdata.setCurrMove(BoardUtil.change(temdata.getCurrMove()));
                    // 将产生的新局面给对方
                    NegaMaxRun betaRun = new NegaMaxRun(temdata, depth - 1);
                    betaRunMoveMap.put(betaRun, curM);
                    list.add(betaRun);
                }
                invokeAll(list);
                for (NegaMaxRun betaRun : list) {
                    MinimaxResult result = betaRun.join();
                    float value = -result.getMark();
                    // 通过向上传递的值修正上下限
                    if (value > best_value) {
                        best_value = value;
                        move = betaRunMoveMap.get(betaRun);
                    }
                }
            } else {
                // 没有可走子 交给对方
                data.setCurrMove(BoardUtil.change(data.getCurrMove()));
                if (GameRule.validMoves(data) > 0) {
                    return new NegaMaxRun(data, depth - 1).fork().join();
                } else {
                    data.setCurrMove(BoardUtil.change(data.getCurrMove()));
                    return MinimaxResult.builder().mark(evaluation.currentValue(data)).build();
                }
            }
            return MinimaxResult.builder().mark(best_value).move(move).build();
        }
    }

}
