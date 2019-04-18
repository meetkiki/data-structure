package miniapp.sort_algorithms.quicksort;

import miniapp.Enum.Constant;
import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;


/**
 * @author Tao
 */
public class DualPivotQuickSort implements SortMethod {

    @Override
    public String getCnName() {
        return "双轴快速排序";
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Teal;
    }
    @Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        dualPivotQuickSort(arr,0,length - 1);
        return arr;
    }

    @Override
    public String methodName() {
        return "DualPivotQuickSort";
    }

    /**
     * 双轴快速排序
     * @param arr
     * @param l
     * @param r
     */
    private void dualPivotQuickSort(int[] arr, int l, int r) {
//        if (r - l < Constant.INSERTSIZE) {
//            insertSort(arr,l,r);
//            return;
//        }
        if (r - l <= 0) {
//            insertSort(arr,l,r);
            return;
        }
        // 保证pivot1 小于等于pivot2
        if (arr[l] > arr[r]){
            swap(arr,l,r);
            // 排除相等情况
        }else if (arr[l] == arr[r]){
            while (arr[l] == arr[r] && l < r){
                l++;
            }
        }
        // 类似于三路快排 有两个中枢 lt 左边小于pivot1 lt和gt之间大于pivot1小于pivot2 gt右边大于pivot2
        int i = l + 1,lt = l,gt = r,pivot1 = arr[l],pivot2 = arr[r];
        out:
        while (i < gt){
            if (arr[i] < pivot1){
                swap(arr, ++lt, i++);
                // 介于pivot1和pivot2之间的数据
            }else if (arr[i] <= pivot2) {
                i++;
            }else { // arr[i] 大于pivot2
                // 先过滤大于pivot2的数据
                while(arr[--gt] > pivot2){
                    if (gt <= i) break out;
                }
                // arr[gt]小于pivot1的数据放在lt左边
                if (arr[gt] < pivot1) {
                    swap(arr, gt, i);
                    swap(arr, ++lt, i++);
                } else { // A[gt] 大于等于pivot1 && A[gt] 小于等于pivot2
                    swap(arr, gt, i++);
                }
            }
        }
        swap(arr, l, lt);
        swap(arr, r, gt);
        // 一次三向切分确定两个元素的位置 这两个元素将数组分为三份
        dualPivotQuickSort(arr,l,lt - 1);
        dualPivotQuickSort(arr,lt + 1,gt - 1);
        dualPivotQuickSort(arr,gt + 1,r);
    }

    @Override
    public void destory() {}


    public static void main(String[] args) {
        int[] arr = {1,2,32,3,22,11,21};
        DualPivotQuickSort dualPivotQuickSort = new DualPivotQuickSort();
        dualPivotQuickSort.sort(arr);
        long sort = dualPivotQuickSort.testSort(10000000);
        System.out.println("DualPivotQuickSort 花费时间"+sort+"ms");
    }
}
