package miniapp.sort_algorithms.shellsort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.Arrays;

/**
 * 希尔排序
 */
public class ShellSort implements SortMethod {

    @Override
    public String getCnName() {
        return "希尔排序";
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.valueOf("Amber");
    }

    /**
     * 希尔排序 交换不相临的元素以对数组进行局部排序，并最终使用插入排序将局部有序的数组排序
     *  优化后的插入排序
     *   1.拆分待排序数组间隔为h个子数组，对每个h数组进行插入排序
     *
     *   输入         122,123,11,332,22,334,545,65465
     *                 |-------------|
     *                      交换
     *   4-sort       22,123,11,332,122,334,545,65465
     *   1-sort       11,22,122,123,332,334,545,65465
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr) {
        int N = arr.length,h = 1;
        // 拆分数组为子数组h, 1,4,13,40,121,364,1093
        while (h < N / 3) h = h * 3 + 1;
        while (h >= 1){
            // 使数组h变得有序 插入排序代码
            for (int i = h; i < N; i++) {
                int temp = arr[i];
                int j = i - h;
                do{
                    if (temp < arr[j]){
                        arr[j+h] = arr[j];
                    }else{
                        break;
                    }
                    j -= h;
                }while (j >= 0);
                arr[j + h] = temp;
            }
            h /= 3;
        }
        return arr;
    }

    public static void main(String[] args) {
//        int[] arr = {22,123,11,332,122,334,545,65465,23,231,65,44,78,87,98,989,10};
        int[] arr = {122,123,11,332,22,334,545,65465};
        ShellSort shellSort = new ShellSort();
        shellSort.sort(arr);
        System.out.println(Arrays.toString(arr));

    }


}
