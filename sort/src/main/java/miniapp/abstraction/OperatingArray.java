package miniapp.abstraction;

/**
 * 抽象操作
 */
public interface OperatingArray {

    /**
     * 比较两个数的大小返回布尔型  判断A是否大于B
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    default boolean compareMore(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        return currentCompareIndex > currentChangeIndex;
    }

    /**
     * 比较两个数的大小返回布尔型  判断A是否小于B
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    default boolean compareLess(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        return currentCompareIndex < currentChangeIndex;
    }

    /**
     * 比较两个数的大小返回布尔型  判断A是否大于等于B
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    default boolean compareMoreOrEqual(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        return currentCompareIndex >= currentChangeIndex;
    }

    /**
     * 比较两个数的大小返回布尔型  判断A是否小于等于B
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    default boolean compareLessOrEqual(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        return currentCompareIndex <= currentChangeIndex;
    }

}
