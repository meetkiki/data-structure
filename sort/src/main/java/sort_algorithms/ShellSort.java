package sort_algorithms;

import abstraction.SortMethod;
import abstraction.SortView;
import view.AlgoFrame;

import java.util.Arrays;

/**
 * 希尔排序
 */
public class ShellSort implements SortMethod, SortView {

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

    /**
     * 希尔排序可视化
     *  1.插入排序的缺陷
     *      对于大规模数据插入排序只会一个一个的将数据移动到右边
     *      希尔排序为了加快速度改进了插入排序 交换不相临数组使数组局部有序
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        frame.setData(0, -1, -1);
        int size = frame.length(),h = 1;
        // 拆分数组为子数组h, 1,4,13,40,121,364,1093
        while (h < size / 3) h = h * 3 + 1;
        while (h >= 1){
            // 插入排序
            for( int i = h ; i < frame.length() ; i ++ ){
                frame.setData(i + 1, i, i - h);
                // 寻找[h, n)区间里的最小值的索引
                for( int j = i; (j - h) >= 0 && frame.less(j,j - h) ; j -= h){
                    frame.setData(i + 1, j, j - h);
                    frame.swap(j,j - h);
                }
            }
            // 排序局部数组
            h /= 3;
        }
        frame.setData(frame.length(),-1,-1);
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
