package miniapp.sort_algorithms;

import miniapp.abstraction.SortMethod;
import miniapp.view.AlgoFrame;

import java.util.Arrays;

public class BubbleSort implements SortMethod {

    @Override
    public void sort(AlgoFrame frame) {
        // 初始化
        int N = frame.length();
        // 排序好空间从右边扩散
        for (int i = 0;frame.compareLess(i, N); i++) {
            // 冒泡排序思想为将未排序区间的最大值往最后冒
            for (int j = N - 1; frame.compareMore(j, i); j --) {
                // 比较左区间内当前值 找出最大值往右冒泡
                if (frame.less(j,j - 1)){
                    frame.swap(j,j - 1);
                }
            }
            // 展示格式化后数据
            frame.updateOrdereds(i + 1);
        }
    }

    /**
     * 冒泡排序
     */
    @Override
    public int[] sort(int[] arr){
        int temp;
        for (int i = 0; i < arr.length; i++) {
            boolean flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j+1]){
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                    flag = true;
                }
            }
            // 没有数据交换，提前推出
            if (!flag) break;
        }
        return arr;
    }

    @Override
    public String methodName() {
        return "Bubble Sort";
    }


    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,22,2};
        System.out.println(Arrays.toString(new BubbleSort().sort(arr)));
    }

}
