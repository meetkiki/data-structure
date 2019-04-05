package miniapp.sort_algorithms.quicksort;

import miniapp.abstraction.Constant;
import miniapp.abstraction.SortMethod;

public class DualPivotQuickSort implements SortMethod {


    @Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        dualPivotQuickSort(arr,0,length - 1);
        return arr;
    }

    /**
     * 双轴快速排序
     * @param arr
     * @param l
     * @param r
     */
    private void dualPivotQuickSort(int[] arr, int l, int r) {
        if (r - l < Constant.INSERTSIZE) {
            insertSort(arr,l,r);
            return;
        }
        // 保证pivot1 小于等于pivot2
        if (arr[l] > arr[r]){
            swap(arr,l,r);
        }
        int i = l,j = r,k = l + 1,pivot1 = arr[l],pivot2 = arr[r];
        OUT_LOOP:while (k < j){
            if (arr[k] < pivot1){
                swap(arr,++i,k++);
            }else if (arr[k] >= pivot1 && arr[k] <= pivot2){
                k++;
            }else{
                // j先增减，j首次扫描pivot2就不参与其中
                while (arr[--j] > pivot2) {
                    if (j <= k) {
                        // 扫描终止
                        break OUT_LOOP;
                    }
                }
                if (arr[j] < pivot1) {
                    swap(arr, j, k);
                    swap(arr, ++i, k++);
                } else {
                    swap(arr, j, k++);
                }
            }
        }
        swap(arr, l, i);
        swap(arr, r, j);
        dualPivotQuickSort(arr,l,i - 1);
        dualPivotQuickSort(arr,i + 1,j - 1);
        dualPivotQuickSort(arr,j + 1,r);
    }



    public static void main(String[] args) {
        DualPivotQuickSort dualPivotQuickSort = new DualPivotQuickSort();
        long sort = dualPivotQuickSort.testSort(100000000);
        System.out.println("DualPivotQuickSort 花费时间"+sort+"ms");
    }
}
