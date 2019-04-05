package miniapp.sort_algorithms.quicksort;

public class Quick1ScanSort extends QuickSort {


    /**
     * 这里的处理有点类似选择排序。我们通过游标 i 把 A[p…r-1] 分成两部分。
     * A[p…i-1] 的元素都是小于 pivot 的，我们暂且叫它“已处理区间”，A[i…r-1] 是“未处理区间”。
     *  我们每次都从未处理的区间 A[i…r-1] 中取一个元素 A[j]，
     *  与 pivot 对比，如果小于 pivot，则将其加入到已处理区间的尾部，也就是 A[i] 的位置。
     * @param arr
     * @param l
     * @param r
     * @return
     */
    @Override
    protected int partition(int[] arr, int l, int r) {
        int i = l,pivor = arr[r];
        for (int j = l; j <= r - 1; j++) {
            if (arr[j] < pivor){
                swap(arr,i,j);
                i++;
            }
        }
        swap(arr,i,r);
        return i;
    }

    @Override
    public String getCnName() {
        return "单向扫描快速排序";
    }
}
