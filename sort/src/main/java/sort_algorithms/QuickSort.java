package sort_algorithms;

import abstraction.SortMethod;
import view.AlgoFrame;

import java.util.Arrays;

public class QuickSort implements SortMethod {

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

//    /**
//     * 三向切分快速排序
//     *
//     * @param arr
//     * @param l
//     * @param r
//     */
//    private void quick_sort_c(int[] arr, int l, int r) {
//        if (r <= l) return;
//        int i = l,j = l + 1,k = r,v = arr[l];
//        // 荷兰国旗问题
//        while (j <= k){
//            if (arr[j] < v) swap(arr,i++,j++);
//            else if (arr[j] > v) swap(arr,j,k--);
//            else j++;
//        }
//        // 现在arr[l...i-1] < v < arr[i...j] < arr[k+1,r]成立
//        quick_sort_c(arr,l,i-1);
//        quick_sort_c(arr,k+1,r);
//    }


    private void quick_sort_c(int[] arr, int l, int r) {
        if (l >= r) return;
        // 获取分区点
        int q = partition(arr,l,r);
        quick_sort_c(arr,l,q-1);
        quick_sort_c(arr,q+1,r);
    }

//    /**
//     * 快速排序拆分
//     *  思路：
//     *      1.采用arr[l]作为切分元素
//     *      2.定义一个元素从左边扫描找到第一个大于arr[l]，在从右边往左边扫描找到第一个小于arr[l]的元素
//     *      3.交换这两个元素的值，如此可以保证左边的元素小于arr[l]，右边的j元素大于arr[l]
//     *      4.重复2-3操作，直至左右指针相遇
//     * @param arr
//     * @param l
//     * @param r
//     * @return
//     */
//    private int partition(int[] arr, int l, int r) {
//        int i = l,j = r + 1,v = arr[l];
//        while (true){
//            /**
//             * 找到第一个大于arr[i]的值
//             */
//            while (arr[++i] < v){
//                if (i == r) break;
//            }
//            /**
//             * 找到第一个小于arr[i]的值
//             */
//            while (v < arr[--j]){
//                if (j == l) break;
//            }
//            if (i >= j) break;
//            swap(arr,i,j);
//        }
//        swap(arr,l,j);
//        return j;
//    }

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
     * 快速排序可视化
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        int length = frame.length();
        quickSort(frame,0,length-1);
    }


    /**
     * 插入排序
     * @param frame
     * @param l
     * @param r
     */
    private void InsertSort(AlgoFrame frame, int l, int r){
        for (int i = l;frame.compareLessOrEqual(i, r); i++) {
            // 假定[l,l+1]是有序的 则循环后面的元素找到他们在有序数组中的位置
            for (int j = i; frame.compareMore(j, l) && frame.less(j,j - 1); j--) {
                frame.swap(j,j - 1);
            }
            frame.updateOrdereds(l,i);
        }
        frame.updateOrdereds(l,r + 1);
    }

//    private void quickSort(AlgoFrame frame, int l, int r) {
//        // 小数组优化
//        if (frame.compareMoreOrEqual(l, r - INSERTSIZE)) {
//            InsertSort(frame,l,r);
//            return;
//        }
//        // 获取分区点
//        int q = partition(frame,l,r);
//        quickSort(frame,l,q-1);
//        frame.updateOrdereds(l,q-1);
//        quickSort(frame,q+1,r);
//        frame.updateOrdereds(q+1,r);
//    }

//    /**
//     * 快速排序优化
//     *
//     * @param frame
//     * @param l
//     * @param r
//     * @return
//     */
//    private int partition(AlgoFrame frame, int l, int r) {
//        int i = l;
//        for (int j = l; frame.compareLessOrEqual(j, r - 1); j++) {
//            if (frame.less(j,r)){
//                frame.swap(i++,j);
//            }
//        }
//        frame.swap(i,r);
//        return i;
//    }

    /**
     * 三向快速排序
     * @param frame
     * @param l
     * @param r
     */
    private void quickSort(AlgoFrame frame, int l, int r) {
        if (r <= l) return;
        int i = l,j = l + 1,k = r ,v = frame.get(l);
        while (j <= k){
            int compare = frame.compare(frame.get(j), v);
            if (compare < 0) frame.swap(i++,j++);
            else if(compare > 0) frame.swap(j,k--);
            else j++;
        }
        quickSort(frame,l,i-1);
        frame.updateOrdereds(l,i-1);
        quickSort(frame,k + 1,r);
        frame.updateOrdereds(k + 1,r);
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
