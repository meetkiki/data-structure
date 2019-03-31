package sort_algorithms;

import abstraction.SortMethod;
import view.AlgoFrame;

import java.util.Arrays;

public class BubbleSort implements SortMethod {

    @Override
    public void sort(AlgoFrame frame) {
        // 初始化
        int N = frame.length();
        // 排序好空间从右边扩散
        frame.setData(N,N, -1, -1);
        for (int i = 0; i < N; i++) {
            // 冒泡排序思想为将未排序区间的最大值往最后冒
            frame.setData(N - i,N, N - 1 - i, -1);
            for (int j = 0; j < N - 1 - i; j++) {
                // 比较左区间内当前值 找出最大值往右冒泡
                if (frame.less(j + 1,j)){
                    frame.setData(N - i,N, j + 1, j);
                    frame.swap(j,j + 1);
                }
            }
            // 展示格式化后数据
            frame.setData(N - i,N, -1, -1);
        }
        // 展示格式化后数据
        frame.setData(N, -1, -1);
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
