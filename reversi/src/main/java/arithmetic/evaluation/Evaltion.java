package arithmetic.evaluation;


import bean.BoardChess;
import common.WeightEnum;

/**
 * @ClassName Evaltion 估值抽象
 * @author ypt
 * @date 2019/5/29 11:44
 */
public interface Evaltion {

    /**
     * 根据信息计算得分
     * @param weightEnum
     * @param weight
     * @param data
     * @return
     */
    int weightScore(WeightEnum weightEnum, int weight, BoardChess data);

}
