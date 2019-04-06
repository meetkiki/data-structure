package miniapp.sort_algorithms.quicksort;

import miniapp.Enum.LineColorEnum;

public class Quick3waySort extends QuickSort {

    /**
     * 三向切分快速排序
     *
     * @param arr
     * @param l
     * @param r
     */
    @Override
    protected void quickSort(int[] arr, int l, int r) {
        if (r <= l) return;
        int i = l,j = l + 1,k = r,v = arr[l];
        // 荷兰国旗问题
        while (j <= k){
            if (arr[j] < v) swap(arr,i++,j++);
            else if (arr[j] > v) swap(arr,j,k--);
            else j++;
        }
        // 现在arr[l...i-1] < v < arr[i...j] < arr[k+1,r]成立
        quickSort(arr,l,i-1);
        quickSort(arr,k+1,r);
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.valueOf("LightGreen");
    }

    @Override
    public String getCnName() {
        return "三向切分快速排序";
    }
}
