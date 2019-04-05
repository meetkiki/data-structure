package miniapp.abstraction;

import miniapp.view.AlgoFrame;
import miniapp.entity.SortData;

import java.util.Arrays;
import java.util.Random;

public interface SortMethod {

    /**
     * 当数组长度小于这个数字时使用插入排序
     */
    int INSERTSIZE = 47;

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
        int[] clone = ints.clone();
        long start1 = System.currentTimeMillis();
        sort.sort(ints);
        long end1 = System.currentTimeMillis() - start1;
        long start2 = System.currentTimeMillis();
        Arrays.sort(clone);
        long end2 = System.currentTimeMillis() - start2;
        System.out.println("sorted is " + isSorted(ints));
        System.out.println("Arrays sort is " + end2 + "ms");
        return end1;
    }

    /**
     * 判断是否有序
     * @param arr
     * @return
     */
    default boolean isSorted(int[] arr){
        return isSorted(arr,0,arr.length-1);
    }

    /**
     * 判断是否有序
     * @param arr
     * @return
     */
    default boolean isSorted(int[] arr,int l,int r){
        for (int i = l + 1; i <= r; i++) {
            if (arr[i] < arr[i - 1]){
                return false;
            }
        }
        return true;
    }

    /**
     * 交换方法
     * @param arr
     * @param i
     * @param j
     */
    default void swap(int[] arr, int i, int j) {
        if (i != j){
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }

    /**
     * 判断是否有序
     * @param sortData
     * @return
     */
    default boolean isSorted(SortData sortData){
        return isSorted(sortData,0,sortData.size()-1);
    }

    /**
     * 判断是否有序
     * @param sortData
     * @return
     */
    default boolean isSorted(SortData sortData,int l,int r){
        return isSorted(sortData.getNumbers(),l,r);
    }

    /**
     * 经典插入排序
     *  for (int i = left, j = i; i < right; j = ++i) {
     *      int ai = a[i + 1];
     *      while (ai < a[j]) {
     *          a[j + 1] = a[j];
     *          if (j-- == left) {
     *              break;
     *          }
     *      }
     *      a[j + 1] = ai;
     *  }
     * @param arr
     * @param l
     * @param r
     */
    default void insertSort(int[] arr,int l,int r){
        for (int i = l,j = i; i < r; j = ++i) {
            int temp = arr[i];
            while (temp < arr[j]){
                // 选取temp放在该放的位置上 这里是temp小于arr[j]时将arr[j]右移
                arr[j + 1] = arr[j];
                if (j-- == l){
                    break;
                }
            }
            arr[j + 1] = temp;
        }
    }


    /**
     * 排序方法名称
     * @return
     */
    String methodName();

}
