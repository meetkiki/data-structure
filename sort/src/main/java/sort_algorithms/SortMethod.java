package sort_algorithms;

import view.AlgoFrame;
import entity.SortData;

import java.util.Random;

public interface SortMethod {

    /**
     * 排序可视化
     * @param frame
     * @return
     */
    void sort(AlgoFrame frame);

    /**
     * 基本排序算法
     * @param arr
     * @return
     */
    int[] sort(int[] arr);

    /**
     * 测试排序的效率
     *
     * @param sort
     * @param n
     * @return
     */
    default long testSort(SortMethod sort,int n){
        Random random = new Random();
        int[] ints = new int[n];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt(n);
        }
        long start = System.currentTimeMillis();
        sort.sort(ints);
        return System.currentTimeMillis() - start;
    }


    /**
     * 判断是否有序
     * @param arr
     * @return
     */
    default boolean isSorted(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否有序
     * @param sortData
     * @return
     */
    default boolean isSorted(SortData sortData){
        return isSorted(sortData.getNumbers());
    }

    /**
     * 排序方法名称
     * @return
     */
    String methodName();

}
