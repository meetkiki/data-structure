package miniapp.abstraction;

import miniapp.entity.SortData;
import miniapp.view.AlgoFrame;

import java.util.Random;

public interface Sort {


    /**
     * 基本排序算法
     * @param arr
     * @return
     */
    default int[] sort(int[] arr){return null;};

    /**
     * 排序可视化
     * @param frame
     * @return
     */
    default void sort(AlgoFrame frame){};


    /**
     * 排序方法名称
     * @return
     */
    default String methodName(){return "";}

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
     * 判断是否有序
     * @param sortData
     * @return
     */
    default boolean isSorted(SortData sortData, int l, int r){
        return isSorted(sortData.getNumbers(),l,r);
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
     * @param arr
     * @return
     */
    default boolean isSorted(int[] arr){
        return isSorted(arr,0,arr.length-1);
    }


    default int[] randomInt(int n){
        int[] rs = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            rs[i] = random.nextInt(n);
        }
        return rs;
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
}
