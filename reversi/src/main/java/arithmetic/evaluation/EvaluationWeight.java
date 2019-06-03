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

    public EvaluationWeight(WeightIndividual individual){
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

//    static {
//        for (int status = 0; status < cacheWeight.length; status++) {
//            cacheWeight[status] = new float[WeightEnum.values().length];
//            GameStatus gameStatus = GameStatus.findStatus(status);
//            // 如果没有 默认为0
//            switch (gameStatus){
//                case OPENING:
//                    cacheWeight[status][WeightEnum.MOBILITY.getIndex()] = 11;
//                    cacheWeight[status][WeightEnum.POSVALUE.getIndex()] = 1;
//                    cacheWeight[status][WeightEnum.COUNT.getIndex()] = 1;
//                    cacheWeight[status][WeightEnum.STABISTOR.getIndex()] = 5;
//                    cacheWeight[status][WeightEnum.FRONTIERS.getIndex()] = -2;
//                    cacheWeight[status][WeightEnum.INNER.getIndex()] = -1;
//                    cacheWeight[status][WeightEnum.PARITY.getIndex()] = 2;
//                    break;
//                case MIDDLE:
//                    cacheWeight[status][WeightEnum.MOBILITY.getIndex()] = 8;
//                    cacheWeight[status][WeightEnum.POSVALUE.getIndex()] = 1;
//                    cacheWeight[status][WeightEnum.COUNT.getIndex()] = 1;
//                    cacheWeight[status][WeightEnum.STABISTOR.getIndex()] = 11;
//                    cacheWeight[status][WeightEnum.FRONTIERS.getIndex()] = -2;
//                    cacheWeight[status][WeightEnum.INNER.getIndex()] = -1;
//                    cacheWeight[status][WeightEnum.PARITY.getIndex()] = 5;
//                    break;
//                case OUTCOME:
//                case END:
//                    cacheWeight[status][WeightEnum.MOBILITY.getIndex()] = 8;
//                    cacheWeight[status][WeightEnum.POSVALUE.getIndex()] = 2;
//                    cacheWeight[status][WeightEnum.COUNT.getIndex()] = 2;
//                    cacheWeight[status][WeightEnum.STABISTOR.getIndex()] = 15;
//                    cacheWeight[status][WeightEnum.FRONTIERS.getIndex()] = 0;
//                    cacheWeight[status][WeightEnum.INNER.getIndex()] = 2;
//                    cacheWeight[status][WeightEnum.PARITY.getIndex()] = 5;
//                    break;
//                default:break;
//            }
//        }
//    }

    /**
     * 获取权重信息
     * @param status
     * @param weightEnum
     * @return
     */
    public final float getWeight(GameStatus status,WeightEnum weightEnum){
        return cacheWeight[status.getStatus()][weightEnum.getIndex()];
    }

}
