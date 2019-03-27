/**
 * 堆排序
 */
public class HeapSort implements SortMethod{

    /**
     * 堆排序
     *  1.构建最大堆，完成之后则顶部为则为最大值
     *  2.将最大值放在最后，在剩下的n-1个元素中构建最大堆
     *  3.将最大值放在最后，重复上面的操作，直到
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr){
        buildHeap(arr);
        int k = arr.length - 1;
        while (k > 0){
            // 每次循环都是将最大值放在k的位置，k为排序好的索引
            swap(arr,0,k);
            --k;
            heapify(arr,k,0);
        }
        return arr;
    }

    /**
     * 构建堆 构建最大堆 
     *  1.只需要构造非叶子结点，即只需要构建0-n/2个结点
     *  2.对n/2个结点堆化,将最大值放在堆顶
     * @param arr
     */
    private void buildHeap(int[] arr) {
        for (int i = 0; i < (arr.length >> 1); i++) {
            heapify(arr,arr.length - 1, i);
        }
    }

    /**
     * 堆化
     *  堆化就是比较当前元素和父节点的大小，保持父节点为最大结点
     * @param arr
     * @param length
     * @param i
     */
    private void heapify(int[] arr, int length, int i) {
        while (true){
            int maxpos = i;
            if ((i<<1) <= length && arr[i<<1] > arr[i]) maxpos = (i<<1);
            if ((maxpos+1)<=length && arr[maxpos + 1] > arr[maxpos]) maxpos+=1;
            if (maxpos == i) return;
            swap(arr,maxpos,i);
            i = maxpos;
        }
    }

    /**
     * 交换两数的值
     * @param arr
     * @param maxpos
     * @param i
     */
    private void swap(int[] arr, int maxpos, int i) {
        int temp = arr[i];
        arr[i] = arr[maxpos];
        arr[maxpos] = temp;
    }


    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        long sort = heapSort.testSort(heapSort, 100000);
        System.out.println("花费时间"+sort+"ms");
    }
}
