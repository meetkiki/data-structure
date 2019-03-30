package sort_algorithms;

import view.AlgoFrame;
import entity.SortData;

public class QuickSort implements SortMethod{

    /**
     * 快速排序
     *  快速排序的基本思想是，通过一轮的排序将序列分割成独立的两部分，其中一部分序列的关键字（这里主要用值来表示）
     *      均比另一部分关键字小。继续对长度较短的序列进行同样的分割，最后到达整体有序
     *
     * 思想: 1.在待排序的元素任取一个元素作为基准(通常选第一个元素，但最的选择方法是从待排序元素中随机选取一个作为基准)，称为基准元素；
     *
     *       2.将待排序的元素进行分区，比基准元素大的元素放在它的右边，比其小的放在它的左边；
     *
     *       3.对左右两个分区重复以上步骤直到所有元素都是有序的。
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr){
        quick_sort_c(arr,0,arr.length-1);
        return arr;
    }

    private void quick_sort_c(int[] arr, int l, int r) {
        if (l >= r) return;
        // 获取分区点
        int q = partition(arr,l,r);
        quick_sort_c(arr,l,q-1);
        quick_sort_c(arr,q+1,r);
    }

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
    private int partition(int[] arr, int l, int r) {
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

    /**
     * 交换方法
     * @param arr
     * @param i
     * @param j
     */
    private void swap(int[] arr, int i, int j) {
        if (i != j){
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }

    @Override
    public void sort(AlgoFrame frame) {

    }

    @Override
    public String methodName() {
        return "Quick Sort";
    }

    public static void main(String[] args) {
//        int[] arr = {1,2,32,3,22,11,21};
//        System.out.println(Arrays.toString(new sort_algorithms.QuickSort().sort(arr)));
        QuickSort quickSort = new QuickSort();
        long sort = quickSort.testSort(quickSort, 100000000);
        System.out.println("花费时间"+sort+"ms");

        // 花费时间1061ms   花费时间1424ms  花费时间1165ms
    }
}
