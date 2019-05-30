package arithmetic.evaluation;

import bean.BoardChess;
import common.Constant;
import common.WeightEnum;

import static common.Constant.MODEL;
/**
 * @author ypt
 * @ClassName CalculationEvaltion 计算估值
 * @date 2019/5/29 11:43
 */
public class CalculationEvaltion implements Evaltion{

    /**
     * 根据权重类型获取分数
     * @param weightEnum
     * @param data
     * @return
     */
    @Override
    public final float weightScore(WeightEnum weightEnum, float weight, BoardChess data) {
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
                return (player == Constant.WHITE ? weight : -weight) * (data.getWInners().size() - data.getBInners().size());
            case FRONTIERS:
                return (player == Constant.WHITE ? weight : -weight) * (data.getWfrontiers().size() - data.getBfrontiers().size());
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


}
