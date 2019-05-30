package bean;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Gameplayer {

    /**
     * 是否先手
     */
    private boolean first;

    /**
     * 赢子数
     */
    private Byte winCount;

    /**
     * 得分
     */
    private int count;
    /**
     * 胜利者
     */
    private WeightIndividual winner;

    /**
     * 基因序列A
     */
    private WeightIndividual evaluationA;

    /**
     * 基因序列B
     */
    private WeightIndividual evaluationB;

}
