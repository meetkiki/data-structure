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
import lombok.extern.log4j.Log4j2;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static arithmetic.genetic.GeneticAlgorithm.entitysize;
import static common.Constant.NULL;

/**
 * 计算调度
 * @author Tao
 */
@Log4j2
public class GameManager {
    private static final AtomicInteger ati = new AtomicInteger(0);
    /**
     * 存储正在进行的对局
     */
    private static final Map<WeightIndividual,WeightIndividual> ongoing = new ConcurrentHashMap<>(entitysize);

    /**
     * 计算总调度
     *
     *  返回每个基因组对局信息
     */
    public static final Map<WeightIndividual, List<Gameplayer>> chief_dispatcher(List<WeightIndividual> weightIndividuals){
        log.info("父代对战开始 ! ");
        ati.set(0);
        int size = weightIndividuals.size();
        // 并行任务 为保证不出现双方先手同时对战 会出现线程安全问题
        List<ForkJoinTask<Gameplayer>> list = new ArrayList<>(size * (size - 1));
        List<Gameplayer> result = new ArrayList<>(size * (size - 1));
        for (WeightIndividual current : weightIndividuals) {
            playGame(weightIndividuals, list, current);
        }
        for (ForkJoinTask<Gameplayer> task : list) {
            Gameplayer gameplayer = GameContext.getCall(task);
            result.add(gameplayer);
        }
        Map<WeightIndividual, List<Gameplayer>> listMap = result.stream().collect(Collectors.groupingBy(Gameplayer::getEvaluationA));
        log.info("父代对战结束 ! 总共进行 " + ati.get() + " 场对局 !");
        return listMap;
    }

    /**
     * 当前选手分别与其他选手对局
     * @param weightIndividuals
     * @param list
     * @param current
     */
    private static void playGame(List<WeightIndividual> weightIndividuals, List<ForkJoinTask<Gameplayer>> list, WeightIndividual current) {
        for (WeightIndividual other : weightIndividuals) {
            if (current.equals(other)){
                // 不和自己对局
                continue;
            }
            SimulationGameThread gameThread = new SimulationGameThread(current, other);
            list.add(GameContext.submit(gameThread));
        }
    }

    /**
     * 给定两组基因 模拟游戏
     * @return
     */
    static class SimulationGameThread implements Callable<Gameplayer> {
        private WeightIndividual weightA;
        private WeightIndividual weightB;

        public SimulationGameThread(WeightIndividual weightA, WeightIndividual weightB) {
            this.weightA = weightA;
            this.weightB = weightB;
        }

        @Override
        public Gameplayer call() {
            // 冲突检测
            collision();
            ati.incrementAndGet();
            Calculator calculatorA = new Calculator(new ReversiEvaluation(weightA)).setPlayer(Constant.BLACK);
            Calculator calculatorB = new Calculator(new ReversiEvaluation(weightB)).setPlayer(Constant.WHITE);
            ongoing.put(weightA,weightB);
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
//                log.info("对局结束! 平局 ");
//            }else{
//                log.info("对局结束! " + winner.getName() + " 获得胜利 ! "
//                        + "\n" + "对应源基因为 " + Arrays.toString(winner.getSrcs()));
//            }
            score = Math.abs(score);
            ongoing.remove(weightA,weightB);
            return Gameplayer.builder()
                    .count(score)
                    .evaluationA(weightA).evaluationB(weightB)
                    .first(calculatorA.getPlayer() == Constant.BLACK ? weightA : weightB)
                    .winner(winner)
                    .build();
        }

        private void collision() {
            while (ongoing.containsValue(weightA) || ongoing.containsKey(weightB)){
                log.trace("出现 对局冲突 当前线程 正在等待... ");
                GameContext.sleep(100);
            }
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
