package arithmetic.evaluation;

import bean.WeightIndividual;
import common.Constant;
import common.GameStatus;
import common.WeightEnum;
import utils.BoardUtil;

/**
 * @author ypt
 * @ClassName EvaluationWeight
 * @Description 权重
 * @date 2019/5/28 15:24
 */
public class EvaluationWeight {
    /**
     * 权重数据
     */
    private final float[][] cacheWeight = new float[GameStatus.values().length][WeightEnum.values().length];

    private final WeightIndividual individual;

    public EvaluationWeight(WeightIndividual individual){
        this.individual = individual;
        initWeight(individual);
    }
    /**
     * 初始化权重信息
     */
    public void initWeight(WeightIndividual individual){
        int[] srcs = individual.getSrcs();
        for (int i = 0; i < srcs.length; i++){
            int status = i % GameStatus.values().length;
            int weight = i % WeightEnum.values().length;
            float genesHelf = (float) (Constant.GENEMAX / 2.0);
            float gene = ((srcs[i] - genesHelf) / genesHelf);
            cacheWeight[status][weight] = gene;
        }
    }

    /**
     * 获取权重信息
     * @param status
     * @param weightEnum
     * @return
     */
    public final float getWeight(GameStatus status,WeightEnum weightEnum){
        return cacheWeight[status.getStatus()][weightEnum.getIndex()];
    }

    public WeightIndividual getIndividual() {
        return individual;
    }
}
