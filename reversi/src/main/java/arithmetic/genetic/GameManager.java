package arithmetic.genetic;

import arithmetic.Calculator;
import arithmetic.evaluation.ReversiEvaluation;
import bean.BoardChess;
import bean.Gameplayer;
import bean.Move;
import bean.WeightIndividual;
import common.Constant;
import common.GameStatus;
import game.GameRule;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (WeightIndividual weightIndividual : weightIndividuals) {
            List<Gameplayer> gameplayers = play_game(weightIndividual, weightIndividuals);
            listMap.put(weightIndividual,gameplayers);
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
    public static List<Gameplayer> play_game(WeightIndividual weightA, List<WeightIndividual> others){
        List<Gameplayer> gameplayers = new ArrayList<>();
        // 分为会两局对局
        for (WeightIndividual other : others) {
            if  (weightA.equals(other)){
                // 不和自己对局
                continue;
            }
            // 存储对局数据
            Gameplayer gameplayer = simulationGame(weightA, other);
            gameplayers.add(gameplayer);
        }
        return gameplayers;
    }

    /**
     * 给定两组基因 模拟游戏
     * @param weightA
     * @param weightB
     * @return
     */
    private static Gameplayer simulationGame(WeightIndividual weightA, WeightIndividual weightB) {
        Calculator calculatorA = new Calculator(new ReversiEvaluation(weightA)).setPlayer(Constant.BLACK);
        Calculator calculatorB = new Calculator(new ReversiEvaluation(weightB)).setPlayer(Constant.WHITE);
        int score;
        BoardChess chess = new BoardChess();
        do {
            // 循环交替下棋 直到棋局结束
            Calculator calculator = choseMove(chess,calculatorA,calculatorB);
            Move move = calculator.searchMove(chess).getMove();
            GameRule.make_move(chess, BoardUtil.squareChess(move));
            if (GameRule.valid_moves(chess) == 0)  GameRule.passMove(chess);
        }while (chess.getStatus() != GameStatus.END);
        WeightIndividual winner = NULL;
        // 设置胜利者
        score = chess.getbCount() - chess.getwCount();
        if (score != Constant.EMPTY){
            winner = score > 0 ? weightA : weightB;
        }
        score = Math.abs(score);
        return Gameplayer.builder()
                .count(score)
                .evaluationA(weightA).evaluationB(weightB)
                .first(calculatorA.getPlayer() == Constant.BLACK)
                .winner(winner)
                .build();
    }

    private static Calculator choseMove(BoardChess chess,Calculator calculatorA,Calculator calculatorB) {
        byte player = chess.getCurrMove();
        if (player == calculatorA.getPlayer()){
            return calculatorA;
        }
        return calculatorB;
    }

}
