package arithmetic.evaluation;

import common.GameStatus;
import common.WeightEnum;

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
    private static final int[][] cacheWeight = new int[GameStatus.values().length][WeightEnum.values().length];

    /**
     * 初始化权重信息
     */
    static {
        for (int status = 0; status < cacheWeight.length; status++) {
            cacheWeight[status] = new int[WeightEnum.values().length];
            GameStatus gameStatus = GameStatus.findStatus(status);
            // 如果没有 默认为0
            switch (gameStatus){
                case OPENING:
                    cacheWeight[status][WeightEnum.MOBILITY.getIndex()] = 11;
                    cacheWeight[status][WeightEnum.POSVALUE.getIndex()] = 1;
                    cacheWeight[status][WeightEnum.COUNT.getIndex()] = 0;
                    cacheWeight[status][WeightEnum.STABISTOR.getIndex()] = 2;
                    cacheWeight[status][WeightEnum.FRONTIERS.getIndex()] = 0;
                    break;
                case MIDDLE:
                    cacheWeight[status][WeightEnum.MOBILITY.getIndex()] = 10;
                    cacheWeight[status][WeightEnum.POSVALUE.getIndex()] = 2;
                    cacheWeight[status][WeightEnum.COUNT.getIndex()] = 2;
                    cacheWeight[status][WeightEnum.STABISTOR.getIndex()] = 10;
                    cacheWeight[status][WeightEnum.FRONTIERS.getIndex()] = 0;
                    cacheWeight[status][WeightEnum.INNER.getIndex()] = 0;
                    cacheWeight[status][WeightEnum.PARITY.getIndex()] = 0;
                    break;
                case OUTCOME:
                    cacheWeight[status][WeightEnum.MOBILITY.getIndex()] = 10;
                    cacheWeight[status][WeightEnum.POSVALUE.getIndex()] = 2;
                    cacheWeight[status][WeightEnum.COUNT.getIndex()] = 8;
                    cacheWeight[status][WeightEnum.STABISTOR.getIndex()] = 12;
                    cacheWeight[status][WeightEnum.FRONTIERS.getIndex()] = 0;
                    cacheWeight[status][WeightEnum.INNER.getIndex()] = 0;
                    cacheWeight[status][WeightEnum.PARITY.getIndex()] = 0;
                    break;
                case END:
                    cacheWeight[status][WeightEnum.COUNT.getIndex()] = 100000;
                    break;
                default:break;
            }
        }
    }

    /**
     * 获取权重信息
     * @param status
     * @param weightEnum
     * @return
     */
    public static final int getWeight(GameStatus status,WeightEnum weightEnum){
        return cacheWeight[status.getStatus()][weightEnum.getIndex()];
    }



}
