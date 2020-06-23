package arithmetic.evaluation;

import bean.BoardChess;
import bean.WeightIndividual;
import common.GameStatus;
import common.WeightEnum;
import game.GameRule;

/**
 * @author tao
 * @ClassName ReversiEvaluation
 * @Description 估值方法
 * @date 2019/5/8 13:58
 */
public class ReversiEvaluation {

    private static final Evaltion evaltion = new CalculationEvaltion();

    private final EvaluationWeight evaluationWeight;
    private final WeightEnum[]  weightEnums = WeightEnum.values();

    /**
     * 传入基因编码
     * @param individual
     */
    public ReversiEvaluation(WeightIndividual individual){
        this.evaluationWeight = new EvaluationWeight(individual);
    }



    /**
     * 估值函数
     *
     * @param data
     * @return
     */
    public float currentValue(BoardChess data) {
        // 更新棋局状态
        GameRule.validMoves(data);
        // 计算内部子
        GameRule.sumInnersFrontiers(data);
        float score = 0;
        GameStatus status = data.getStatus();
        for (WeightEnum weightEnum : weightEnums) {
            float weight = this.evaluationWeight.getWeight(status, weightEnum);
            if (weight == 0){
                continue;
            }
            score += evaltion.weightScore(weightEnum,weight,data);
        }
        return score;
    }


    public EvaluationWeight getEvaluationWeight() {
        return evaluationWeight;
    }
}
