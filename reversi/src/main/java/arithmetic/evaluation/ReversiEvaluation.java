package arithmetic.evaluation;

import bean.BoardChess;
import common.Constant;
import common.GameStatus;
import common.WeightEnum;
import game.GameRule;

import static common.Constant.MAX;
import static common.Constant.MIN;
import static common.Constant.MODEL;

/**
 * @author ypt
 * @ClassName ReversiEvaluation
 * @Description 估值方法
 * @date 2019/5/8 13:58
 */
public class ReversiEvaluation {
    /**
     * 计算次数
     */
    private static int count;

    /**
     * 估值函数
     *
     * @param data
     * @return
     */
    public static int currentValue(BoardChess data) {
        // 更新棋局状态
        GameRule.valid_moves(data);
        data.updateStatus();
        int score = 0;
        count++;
        GameStatus status = data.getStatus();
        for (WeightEnum weightEnum : WeightEnum.values()) {
            int weight = EvaluationWeight.getWeight(status, weightEnum);
            if (weight == 0){
                continue;
            }
            score += weightScore(weightEnum,weight,data);
        }
        return score;
    }

    /**
     * 根据权重类型获取分数
     * @param weightEnum
     * @param data
     * @return
     */
    public static final int weightScore(WeightEnum weightEnum,int weight, BoardChess data){
        byte[] chess = data.getChess();
        byte player = data.getCurrMove(), other = player == Constant.WHITE ? Constant.BLACK : Constant.WHITE;
        switch (weightEnum){
            case MOBILITY:
                return weight * (data.getOurMobility() - data.getOppMobility());
            case POSVALUE:
                return weight * (posEvaluation(chess,player) - posEvaluation(chess,other));
            case COUNT:
                return (player == Constant.WHITE ? weight : -weight) * (data.getwCount() - data.getbCount());
            case STABISTOR:
                return (player == Constant.WHITE ? weight : -weight) * (data.getwStators().size() - data.getbStators().size());
            case INNER:
                return 0;
            case FRONTIERS:
                return 0;
            case PARITY:
                return 0;
            default:break;
        }
        return 0;
    }

    /**
     * //每一个棋子的权重
     */
    private final static int[] evaluation = {
            0,  0,    0,    0,    0,    0,   0,   0,     0,
            0,  100,  -8,   10,   5,    5,   10,  -8,    100,
            0,  -8,   -45,  1,    1,    1,   1,   -45,   -8,
            0,  10,   1,    3,    2,    2,   3,   1,     10,
            0,  5,    1,    2,    1,    1,   2,   1,     5,
            0,  5,    1,    2,    1,    1,   2,   1,     5,
            0,  10,   1,    3,    2,    2,   3,   1,     10,
            0,  -8,   -45,  1,    1,    1,   1,   -45,   -8,
            0,  100,  -8,   10,   5,    5,   10,  -8,    100,
            0,  0,    0,    0,    0,    0,   0,   0,     0,     0
    };

    /**
     * 位置估值
     * @param chess
     * @param player
     * @return
     */
    private static int posEvaluation(byte[] chess, byte player){
        int count = 0;
        for (byte i = 0; i < MODEL; i++) {
            if (chess[i] == player){
                count += evaluation[i];
            }
        }
        return count;
    }

    /**
     * /棋子统计方法
     */
    public static int player_counters(byte[] chess, byte player){
        int count = 0;
        for (byte i = 0; i < MODEL; i++) {
            if (chess[i] == player){
                count ++;
            }
        }
        return count;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        ReversiEvaluation.count = count;
    }
}
