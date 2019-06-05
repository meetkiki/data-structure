package arithmetic.genetic;

import arithmetic.Calculator;
import arithmetic.evaluation.ReversiEvaluation;
import bean.BoardChess;
import bean.Gameplayer;
import bean.Move;
import bean.WeightIndividual;
import common.Constant;
import common.GameStatus;
import game.GameContext;
import game.GameRule;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import static common.Constant.NULL;

/**
 * 计算调度
 * @author Tao
 */
public class GameManager {

    /**
     * 计算总调度
     *
     *  返回每个基因组对局信息
     */
    public static final Map<WeightIndividual, List<Gameplayer>> chief_dispatcher(List<WeightIndividual> weightIndividuals){
        Map<WeightIndividual, List<Gameplayer>> listMap = new HashMap<>(weightIndividuals.size());
        // 并行任务
        List<RecursiveTask<List<Gameplayer>>> list = new ArrayList<>();
        for (WeightIndividual weightIndividual : weightIndividuals) {
            PlayGameThread gameThread = new PlayGameThread(weightIndividual, weightIndividuals);
            GameContext.syncInvoke(gameThread);
            list.add(gameThread);
        }
        for (RecursiveTask<List<Gameplayer>> recursiveTask : list) {
            PlayGameThread task = (PlayGameThread)recursiveTask;
            List<Gameplayer> gameplayers = GameContext.getCall(recursiveTask);
            listMap.put(task.getWeightA(),gameplayers);
        }
        return listMap;
    }


    /**
     * 组织weightA和其他种群游戏
     *
     *  组织一场 都是A先 返回A总分数
     *
     * @return
     */
    static class PlayGameThread extends RecursiveTask<List<Gameplayer>>{

        private WeightIndividual weightA;
        private List<WeightIndividual> others;

        public PlayGameThread(WeightIndividual weightA, List<WeightIndividual> others) {
            this.weightA = weightA;
            this.others = others;
        }

        @Override
        protected List<Gameplayer> compute() {
            List<Gameplayer> gameplayers = new ArrayList<>();
            List<SimulationGameThread> list = new ArrayList<>();
            // 分为会两局对局
            for (WeightIndividual other : others) {
                if  (weightA.equals(other)){
                    // 不和自己对局
                    continue;
                }
                // 存储对局数据
                list.add(new SimulationGameThread(weightA,other));
            }
            invokeAll(list);
            for (SimulationGameThread gameThread : list) {
                Gameplayer gameplayer = gameThread.join();
                gameplayers.add(gameplayer);
            }
            return gameplayers;
        }

        public WeightIndividual getWeightA() {
            return weightA;
        }
    }

    /**
     * 给定两组基因 模拟游戏
     * @return
     */
    static class SimulationGameThread extends RecursiveTask<Gameplayer>{
        private WeightIndividual weightA;
        private WeightIndividual weightB;

        public SimulationGameThread(WeightIndividual weightA, WeightIndividual weightB) {
            this.weightA = weightA;
            this.weightB = weightB;
        }

        @Override
        protected Gameplayer compute() {
            Calculator calculatorA = new Calculator(new ReversiEvaluation(weightA)).setPlayer(Constant.BLACK);
            Calculator calculatorB = new Calculator(new ReversiEvaluation(weightB)).setPlayer(Constant.WHITE);
//            System.out.println(weightA.getName() + " 和 " + weightB.getName() + " 对局开始 " +
//                    (calculatorA.getPlayer() == Constant.BLACK ? weightA.getName() : weightB.getName()) + " 先手");

            int score;
            BoardChess chess = new BoardChess();
            do {
                // 循环交替下棋 直到棋局结束
                Calculator calculator = choseMove(chess,calculatorA,calculatorB);
//            long st = System.currentTimeMillis();
                Move move = calculator.searchMove(chess).getMove();
//            long ed = System.currentTimeMillis();
//            String name = ((AlphaBeta) calculator.getAlphaBeta()).getEvaluation().getEvaluationWeight().getIndividual().getName();
//                 System.out.println(name + " 搜索耗时 ： " + (ed - st) + " ms");
                GameRule.make_move(chess, BoardUtil.squareChess(move));
                if (GameRule.valid_moves(chess) == 0)  GameRule.passMove(chess);
            }while (chess.getStatus() != GameStatus.END);
            WeightIndividual winner = NULL;
            // 设置胜利者
            score = chess.getbCount() - chess.getwCount();
            if (score != Constant.EMPTY){
                winner = score > 0 ? weightA : weightB;
            }
//            if (winner == NULL){
//                System.out.println("对局结束! 平局 ");
//            }else{
//                System.out.println("对局结束! " + winner.getName() + " 获得胜利 ! "
//                        + "\n" + "对应源基因为 " + Arrays.toString(winner.getSrcs()));
//            }
            score = Math.abs(score);
            return Gameplayer.builder()
                    .count(score)
                    .evaluationA(weightA).evaluationB(weightB)
                    .first(calculatorA.getPlayer() == Constant.BLACK ? weightA : weightB)
                    .winner(winner)
                    .build();
        }
    }

    private static Calculator choseMove(BoardChess chess,Calculator calculatorA,Calculator calculatorB) {
        byte player = chess.getCurrMove();
        if (player == calculatorA.getPlayer()){
            return calculatorA;
        }
        return calculatorB;
    }

}
