package miniapp.sort_algorithms.heapsort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.Arrays;

/**
 * 堆排序
 * @author Tao
 */
public class HeapSort implements SortMethod {

    @Override
    public String getCnName() {
        return "堆排序";
    }


    @Override
    public String methodName() {
        return "HeapSort";
    }
    /**
     * 堆排序
     *  1.构建最大堆，完成之后则顶部为则为最大值
     *  2.将最大值放在最后，在剩下的n-1个元素中构建最大堆
     *  3.将最大值放在最后，重复上面的操作，直到整个数组有序
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr){
        buildHeap(arr);
        int k = arr.length - 1;
        while (k >= 1){
            // 每次循环都是将最大值放在k的位置，k为排序好的索引
            swap(arr,0, k--);
            sink(arr,k,0);
        }
        return arr;
    }

    /**
     * 构建堆 构建最大堆 
     *  1.只需要构造非叶子结点下沉，即只需要构建0-n/2个结点
     *  2.对n/2个结点堆化,将最大值放在堆顶
     * @param arr
     */
    private void buildHeap(int[] arr) {
        for (int i = (arr.length - 1 >> 1); i >= 0; i--) {
            sink(arr,arr.length - 1, i);
        }
    }

    /**
     *  下沉方法 如果子节点的值比父节点大 选出最大的儿子替代父亲
     * @param arr
     * @param r
     * @param i
     */
    private void sink(int[] arr, int r, int i) {
        int k;
        while ((k = (i<<1) + 1) <= r){
            // 找到最大子节点
            if ((k < r - 1) && less(arr, k,k + 1)) k++;
            // 如果子节点比父节点小 则直接退出
            if (!less(arr ,i ,k)){
                break;
            }
            swap(arr,i,k);
            i = k;
        }
    }

    /**
     * 比较方法
     * @param arr
     * @param l
     * @param r
     * @return
     */
    private boolean less(int[] arr, int l, int r){
        return arr[l] < arr[r];
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.DeepPurple;
    }

    @Override
    public void destory() {}

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        long sort = heapSort.testSort(10000000);
        System.out.println("花费时间"+sort+"ms");
    }
}
