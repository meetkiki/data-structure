package sort_algorithms;

import entity.SortData;
import view.AlgoFrame;

import java.util.Arrays;

public class MergeSort implements SortMethod{


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
        aux = new int[length];
        mergesort(arr,l,r);
        return arr;
    }

    private void mergesort(int[] arr, int l, int r) {
        // 递归终止条件
        if (l >= r) return;
        // 取 l到r中间的位置
        int q = (l+r)/2;
        // 分治递归
        mergesort(arr,l,q);
        mergesort(arr,q+1,r);
        // 将arr合并
        merge(arr,l,q,r);
    }

    /**
     * 临时数组
     */
    private static int[] aux;
    /**
     * 合并方法
     * @param arr
     * @param l
     * @param q
     * @param r
     */
    private void merge(int[] arr, int l,int q, int r) {
        int i = l,j = q + 1;
        // 将l r复制到aux临时数组
        for (int k = l; k <= r; k++) {
            aux[k] = arr[k];
        }
        // 合并排序 如果左半边取尽 取右边数组 如果右半边取尽 取左边数组
        for (int k = l; k <= r; k++) {
            // 如果左边取尽 取右边
            if (i > q) arr[k] = aux[j++];
            // 如果右边取尽 取左边
            else if (j > r) arr[k] = aux[i++];
            // 合并方法 哪边小先进
            else if (aux[j] < aux[i]) arr[k] = aux[j++];
            else arr[k] = aux[i++];
        }
    }

    /**
     * 临时数组
     */
    private static SortData auxData;

    @Override
    public void sort(AlgoFrame frame) {
        int length = frame.length(),l = 0,r = length - 1;
        aux = new int[length];
        auxData = new SortData(aux);
        // 归并排序初始化
        frame.setData(0, -1, -1);
        mergesort(frame,l,r);
        frame.setData(length,-1,-1);
    }

    /**
     * 归并排序可视化
     * @param frame
     * @param l
     * @param r
     */
    private void mergesort(AlgoFrame frame, int l, int r) {
        if (r <= l) return;
        int mid = l + ((r - l) >> 1);
        // 分治归并
        mergesort(frame,l,mid);
        mergesort(frame,mid + 1,r);
        // 合并两个数组
        merge(frame, l, mid ,r);
    }

    /**
     * 归并排序可视化
     * @param frame
     * @param l
     * @param r
     */
    private void merge(AlgoFrame frame, int l,int mid, int r) {
        int i = l,j = mid + 1;
        // 将数据放入临时数组
        for (int k = l; k <= r; k++) {
            frame.replace(auxData,k,frame.get(k));
        }
        // 归并数组
        for (int k = l; k <= r; k++) {
            if (i > mid) frame.replace(k,auxData.get(j++));
            else if (j > r) frame.replace(k,auxData.get(i++));
            else if (auxData.less(i,j)) frame.replace(k,auxData.get(i++));
            else frame.replace(k,auxData.get(j++));
        }
        // 更新数组排序区间
        frame.setData(l,r+1,l,r);
    }

    @Override
    public String methodName() {
        return "Merge Sort";
    }

    public static void main(String[] args) {
//        MergeSort mergeSort = new MergeSort();
//        long sort = mergeSort.testSort(mergeSort, 10000000);
//        System.out.println("花费时间"+sort+"ms");
        //花费时间1712ms 花费时间1841ms 花费时间1877ms

        int[] arr = {1,2,32,3,22,11,21};
        System.out.println(Arrays.toString(new sort_algorithms.MergeSort().sort(arr)));

    }


}
