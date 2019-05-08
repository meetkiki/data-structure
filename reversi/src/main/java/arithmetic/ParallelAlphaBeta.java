package arithmetic;

import bean.BoardData;
import bean.MinimaxResult;
import bean.Move;
import game.Chess;
import game.GameRule;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import static common.Constant.SIZE;

/**
 * @author ypt
 * @ClassName ParallelAlphaBeta
 * @Description 并行算法
 * @date 2019/5/8 20:35
 */
public class ParallelAlphaBeta {

    public static int Depth = 5;
    public static int MAX = 1000000;
    public static int MIN = -1000000;


    public static MinimaxResult parallelAlphaBeta(BoardData data){
        AlphaBetaRun run = new AlphaBetaRun(data, MIN, MAX, Depth);
        return run.fork().join();
    }

    /**
     * 异步执行计算线程
     */
    static class AlphaBetaRun extends RecursiveTask<MinimaxResult>{
        private BoardData data;
        private int depth;
        private int alpha;
        private int beta;

        public AlphaBetaRun(BoardData data,int alpha, int beta, int depth) {
            this.data = data;
            this.depth = depth;
            this.alpha = alpha;
            this.beta = beta;
        }

        @Override
        protected MinimaxResult compute() {
            // 如果到达预定的搜索深度
            if (depth <= 0) {
                // 直接给出估值
                return MinimaxResult.builder().mark(ReversiEvaluation.currentValue(data, data.getNextmove())).build();
            }
            // 轮到已方走
            Move move = null;
            // 当前最佳估值，预设为负无穷大 己方估值为最小
            int canMove = 0,i = 0;
            if ((canMove = GameRule.valid_moves(data, data.getNextmove())) > 0) {
                boolean[][] moves = data.getMoves();
                Move[] nextmoves = new Move[canMove];
                // 遍历每一种走法
                for(byte row=0;row<SIZE;++row)
                    for(byte col=0;col<SIZE;++col)
                        if (moves[row][col]) nextmoves[i++] = Move.builder().row(row).col(col).build();
                List<AlphaBetaRun> list = new ArrayList<>();
                for (Move curM : nextmoves) {
                    // 创建模拟棋盘
                    BoardData temdata = BoardUtil.copyBoard(data);
                    Chess[][] chess = temdata.getChess();
                    GameRule.removeHint(temdata);
                    //尝试走这步棋
                    GameRule.make_move(chess, curM, temdata.getNextmove(), true);
                    temdata.setNextmove(BoardUtil.change(temdata.getNextmove()));
                    GameRule.valid_moves(temdata, temdata.getNextmove());
                    // 将产生的新局面给对方
                    AlphaBetaRun run = new AlphaBetaRun(temdata, -beta, -alpha, depth - 1);
                    list.add(run);
                }
                invokeAll(list);
                for (AlphaBetaRun run : list) {
                    MinimaxResult result = run.join();
                    int value = -result.getMark();
                    // 剪枝
                    if (value >= beta){
                        return MinimaxResult.builder().mark(beta).move(move).build();
                    }
                    // 通过向上传递的值修正上下限
                    if (value > alpha) {
                        alpha = value;
                        move = result.getMove();
                    }
                }
            } else {
                // 没有可走子 交给对方
                data.setNextmove(BoardUtil.change(data.getNextmove()));
                if (GameRule.valid_moves(data, data.getNextmove()) > 0){
                    return new AlphaBetaRun(data, -beta, -alpha, depth - 1).fork().join();
                }else{
                    return MinimaxResult.builder().mark(ReversiEvaluation.currentValue(data, data.getNextmove())).build();
                }
            }
            return MinimaxResult.builder().mark(alpha).move(move).build();
        }
    }

}
