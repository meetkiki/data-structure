package common;

/**
 * @author ypt
 * @ClassName WeightEnum
 * @Description 权重相关参数
 * @date 2019/5/28 15:26
 */
public enum WeightEnum {
    /**
     * 行动力
     */
    MOBILITY(0),
    /**
     * 坐标位置估值
     */
    POSVALUE(1),
    /**
     * 棋子个数估值
     */
    COUNT(2),
    /**
     * 稳定子
     */
    STABISTOR(3),
    /**
     * 前沿子
     */
    FRONTIERS(4),
    /**
     * 内部子
     */
    INNER(5),
    /**
     * 奇偶性
     */
    PARITY(6),
    ;
    private int index;
    WeightEnum(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
