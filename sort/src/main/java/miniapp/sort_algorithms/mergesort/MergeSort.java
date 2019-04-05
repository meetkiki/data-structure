package miniapp.sort_algorithms.mergesort;

import miniapp.abstraction.SortMethod;

public class MergeSort implements SortMethod{

    /**
     * 临时数组
     */
    protected static int[] aux;

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
        int mid = (l+r)/2;
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
     * @param q
     * @param r
     */
    protected void merge(int[] arr, int l,int q, int r) {
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

    public static void main(String[] args) {
        MergeBu mergeBu = new MergeBu();
        long sort = mergeBu.testSort(100000000);
        System.out.println("花费时间"+sort+"ms");
        //花费时间1712ms 花费时间1841ms 花费时间1877ms

//        MergeSort mergeSort = new MergeSort();
//        Random random = new Random();
//        MergeBu mergeBu = mergeSort.new MergeBu();
//        int[] ints = new int[10000];
//        for (int i = 0; i < ints.length; i++) {
//            ints[i] = random.nextInt(10000);
//        }
//        mergeBu.sort(ints);
//        System.out.println(Arrays.toString(ints));
//        System.out.println(mergeBu.isSorted(ints));
    }

}
