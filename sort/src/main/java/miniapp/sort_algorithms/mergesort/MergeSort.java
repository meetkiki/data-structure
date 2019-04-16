package miniapp.sort_algorithms.mergesort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

/**
 * @author Tao
 */
public class MergeSort implements SortMethod{

    @Override
    public String getCnName() {
        return "归并排序";
    }

    /**
     * ThreadLocal调用remove  防止内存泄漏
     */
    @Override
    public void destory() {
        auxThread.remove();
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Cyan;
    }
    /**
     * 临时数组
     *  线程不安全的用ThreadLocal
     */
    protected ThreadLocal<int[]> auxThread = new ThreadLocal<>();

    /**
     * 归并排序
     *  ① 分解 -- 将当前区间一分为二，即求分裂点 mid = (low + high)/2;
     *  ② 求解 -- 递归地对两个子区间a[low...mid] 和 a[mid+1...high]进行归并排序。递归的终结条件是子区间长度为1。
     *  ③ 合并 -- 将已排序的两个子区间a[low...mid]和 a[mid+1...high]归并为一个有序的区间a[low...high]。
     *      合并的思路
     *          1.创建一个临时数组，从两个子数组的初始索引开始遍历数组放入临时数组
     *          2.判断哪个子数组仍然有数据，放入临时数组
     *          3.将临时数组的数据复制回原数组
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr){
        int length = arr.length,l = 0,r = length - 1;
        auxThread.set(new int[length]);
        mergesort(arr,l,r);
        return arr;
    }

    /**
     * 自顶而下的归并排序
     * @param arr
     * @param l
     * @param r
     */
    private void mergesort(int[] arr, int l, int r) {
        // 递归终止条件
        if (l >= r) return;
        // 取 l到r中间的位置
        int mid = l  + ((r - l) / 2);
        // 分治递归
        mergesort(arr,l,mid);
        mergesort(arr,mid+1,r);
        // 将arr合并
        merge(arr,l,mid,r);
    }

    /**
     * 合并方法
     * @param arr
     * @param l
     * @param mid
     * @param r
     */
    protected void merge(int[] arr, int l,int mid, int r) {
        int[] aux = auxThread.get();
        // 将l r复制到aux临时数组
        for (int k = l; k <= r; k++) {
            aux[k] = arr[k];
        }
        fastMerge(arr,aux,l,mid,r);
    }

    /**
     * 快速归并
     * @param arr 目标数组
     * @param aux 临时数组
     * @param l   左指针
     * @param mid 中指针
     * @param r   右指针
     * @return
     */
    protected int[] fastMerge(int[] arr,int[] aux, int l,int mid, int r){
        int i = l,j = mid + 1;
        // 合并排序 如果左半边取尽 取右边数组 如果右半边取尽 取左边数组
        for (int k = l; k <= r; k++) {
            // 如果左边取尽 取右边
            if (i > mid) arr[k] = aux[j++];
                // 如果右边取尽 取左边
            else if (j > r) arr[k] = aux[i++];
                // 合并方法 哪边小先进
            else if (aux[j] < aux[i]) arr[k] = aux[j++];
            else arr[k] = aux[i++];
        }
        return arr;
    }


    @Override
    public String methodName() {
        return "MergeSort";
    }

    public static void main(String[] args) {
        MergeSort mergeBu = new MergeSort();
        long sort = mergeBu.testSort(10000000);
        System.out.println("花费时间"+sort+"ms");
        //花费时间1562ms 花费时间1596ms 花费时间1606ms
    }

}
