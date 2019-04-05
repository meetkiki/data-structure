package miniapp.sort_algorithms.mergesort;

import miniapp.abstraction.SortMethod;
import miniapp.abstraction.SortVisual;
import miniapp.view.AlgoFrame;

public class MergeBu extends MergeFrame implements SortVisual, SortMethod {
    /**
     * 临时数组
     */
    public static AlgoFrame auxData;

    /**
     * 自底而上的归并排序
     */
    @Override
    public void sort(AlgoFrame frame) {
        int n = frame.length();
        auxData = frame.cloneData();
        // i为子数组的大小
        for (int i = 1;frame.compareLess(i, n); i = i<<1)
            // j为子数组的索引
            for (int j = 0; frame.compareLess(j, n - i); j += i<<1)
                // 归并子数组
                merge(frame,j,j+i-1,Math.min(j+(i<<1)-1,n-1));
    }

    /**
     * 自底而上的归并模式
     *  循序渐进的解决问题
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr) {
        int n = arr.length;
        aux = new int[n];
        // i为子数组的大小
        for (int i = 1; i < n; i = i<<1)
            // j为子数组的索引
            for (int j = 0; j < n - i; j += i<<1)
                // 归并子数组
                merge(arr,j,j+i-1,Math.min(j+(i<<1)-1,n-1));
        return arr;
    }

    @Override
    public String methodName() {
        return "MergeBu Sort";
    }


}
