package common;

/**
 * 置换表存储的类型
 *
 *  估值的类型，EXACT 表示精确值，LOWERBOUND表示下界值，UPPERBOUND 表示上界值
 * @author Tao
 */
public enum EntryType {
    /**
     * 精确值
     */
    EXACT,
    /**
     * 下界值
     */
    LOWERBOUND,
    /**
     * 上界值
     */
    UPPERBOUND

}
