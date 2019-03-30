package sort_algorithms;

import view.AlgoFrame;
import entity.SortData;

import java.util.Arrays;

/**
 * 希尔排序
 */
public class ShellSort implements SortMethod {

    /**
     * 希尔排序 交换不相临的元素以对数组进行局部排序，并最终使用插入排序将局部有序的数组排序
     *  优化后的插入排序
     *   1.拆分待排序数组为h个子数组，对每个h数组进行插入排序
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

    @Override
    public void sort(AlgoFrame frame, SortData data) {
//        frame.setData(0, -1, -1);
//        int N = data.N(),h = 1;
//        // 拆分数组为子数组h, 1,4,13,40,121,364,1093
//        while (h < N / 3) h = h * 3 + 1;
//        while (h >= 1){
//            for( int i = h ; i < data.N() ; i ++ ){
//                // 寻找[h, n)区间里的最小值的索引
//                int minIndex = i;
//                frame.setData(i, -1, minIndex);
//                for( int j = i + 1 ; j < data.N() ; j ++ ){
//                    frame.setData(i, j, minIndex);
//
//                    if( data.get(j) < data.get(minIndex) ){
//                        minIndex = j;
//                        frame.setData(i, j, minIndex);
//                    }
//                }
//                data.swap(i , minIndex);
//                frame.setData(i + 1, -1, -1);
//            }
//            h /= 3;
//        }
//        frame.setData(data.N(),-1,-1);
    }

    @Override
    public String methodName() {
        return "Shell Sort";
    }

    public static void main(String[] args) {
//        int[] arr = {22,123,11,332,122,334,545,65465,23,231,65,44,78,87,98,989,10};
        int[] arr = {122,123,11,332,22,334,545,65465};
        ShellSort shellSort = new ShellSort();
        shellSort.sort(arr);
        System.out.println(Arrays.toString(arr));

    }


}
