package miniapp.sort_algorithms.quicksort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

public class QuickSort implements SortMethod {

    @Override
    public String methodName() {
        return "QuickSort";
    }

    @Override
    public String getCnName() {
        return "快速排序";
    }

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
        quickSort(arr,0,arr.length-1);
        return arr;
    }

    /**
     * 快速排序递归实现
     * @param arr
     * @param l
     * @param r
     */
    protected void quickSort(int[] arr, int l, int r) {
        if (l >= r) return;
        // 获取分区点
        int q = partition(arr,l,r);
        quickSort(arr,l,q-1);
        quickSort(arr,q+1,r);
    }

    /**
     * 快速排序拆分
     *  思路：
     *      1.采用arr[l]作为切分元素
     *      2.定义一个元素从左边扫描找到第一个大于arr[l]，在从右边往左边扫描找到第一个小于arr[l]的元素
     *      3.交换这两个元素的值，如此可以保证左边的元素小于arr[l]，右边的j元素大于arr[l]
     *      4.重复2-3操作，直至左右指针相遇
     * @param arr
     * @param l
     * @param r
     * @return
     */
    protected int partition(int[] arr, int l, int r) {
        int i = l,j = r + 1,v = arr[l];
        while (true){
            /**
             * 找到第一个大于arr[i]的值
             */
            while (arr[++i] < v){
                if (i == r) break;
            }
            /**
             * 找到第一个小于arr[i]的值
             */
            while (v < arr[--j]){
                if (j == l) break;
            }
            if (i >= j) break;
            swap(arr,i,j);
        }
        swap(arr,l,j);
        return j;
    }

    @Override
    public void destory() {}

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.valueOf("DeepOrange");
    }

    public static void main(String[] args) {
//        int[] arr = {1,2,32,3,22,11,21};
//        System.out.println(Arrays.toString(new miniapp.sort_algorithms.quicksort.QuickSort().sort(arr)));
        QuickSort quickSort = new QuickSort();
        long sort = quickSort.testSort(100000000);
        System.out.println("花费时间"+sort+"ms");
        // 花费时间1061ms   花费时间1424ms  花费时间1165ms
    }
}
