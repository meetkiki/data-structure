package miniapp.sort_algorithms.mergesort;

import miniapp.Enum.LineColorEnum;

public class MergeBuSort extends MergeSort {

    /**
     * 自底而上的归并模式
     *  循序渐进的解决问题
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr) {
        int n = arr.length;
        auxThread.set(new int[n]);
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
        return "MergeBuSort";
    }
    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.valueOf("Blue");
    }

    @Override
    public String getCnName() {
        return "自底而上的归并排序";
    }
}
