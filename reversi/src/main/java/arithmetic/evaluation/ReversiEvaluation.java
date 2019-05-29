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

    private static Evaltion evaltion = new CalculationEvaltion();

    /**
     * 终局估值 返回最终估值
     * @param data
     * @return
     */
    public static int endValue(BoardChess data) {
        count++;
        int score;
        byte player = data.getCurrMove();
        // 只考虑棋子数
        int count = data.getwCount() - data.getbCount();
        count = (player == Constant.WHITE ? 1 : -1) * count;
        score = count ==  0 ? 0 : (count >  0 ? MAX : MIN);
        return score;
    }
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
        if (status == GameStatus.END){
            return endValue(data);
        }else{
            for (WeightEnum weightEnum : WeightEnum.values()) {
                int weight = EvaluationWeight.getWeight(status, weightEnum);
                if (weight == 0){
                    continue;
                }
                score += evaltion.weightScore(weightEnum,weight,data);
            }
            return score;
        }
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
