package sort_algorithms;

import abstraction.Optimized;
import abstraction.SortMethod;
import view.AlgoFrame;


public class MergeSort implements SortMethod, Optimized {

    /**
     * 当数组长度小于这个数字时使用插入排序
     */
    private static final int INSERTSIZE = 10;

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
    private static AlgoFrame auxData;

    @Override
    public void sort(AlgoFrame frame) {
        int length = frame.length(),l = 0,r = length - 1;
        auxData = frame.cloneData();
        // 归并排序初始化
        frame.setData(0, -1, -1);
        // 初始归并
        mergesort(frame,l,r);
        frame.setData(length,-1,-1);
    }

    /**
     * 改进接口实现
     * @param frame
     */
    @Override
    public void optimizedSort(AlgoFrame frame) {
        int length = frame.length(),l = 0,r = length - 1;
        // 归并排序初始化
        AlgoFrame auxFrame = frame.cloneData();
        auxFrame.optimizeSetData(frame,-1,0, -1, -1);
        // 优化后归并
        optimizeMerge(auxFrame,frame,l,r);
        auxFrame.optimizeSetData(frame,-1,length,-1,-1);
    }


    /**
     * 归并排序改进后方法
     * @param auxFrame  拷贝数组
     * @param frame     原数组
     * @param l
     * @param r
     */
    private void optimizeMerge(AlgoFrame auxFrame, AlgoFrame frame, int l, int r) {
        /**
         * 归并排序优化① 对于小规模数据使用插入排序
         */
        if (r - l <= INSERTSIZE){
            InsertSort(auxFrame,frame,l,r);
            return;
        }
        int mid = l + ((r - l) >> 1);
        /**
         * 归并排序优化③ 通过再递归中交换参数来避免每次归并时都要复制数组到辅助数组
         */
        optimizeMerge(frame,auxFrame,l,mid);
        optimizeMerge(frame,auxFrame,mid + 1,r);
        /**
         * 归并排序优化② 这里可以判断已经有序 则不需要创建数组归并
         *  两个子数组已经有序 判断两个数组的位置是否有序即可
         */
        if(auxFrame.lessOrEqual(mid,mid+1)){
            // 更新数组排序区间
            auxFrame.dataCoppy(auxFrame,l,frame,l,r - l + 1);
            auxFrame.optimizeSetData(frame,l,r + 1,l,r + 1);
            return;
        }
        merge(auxFrame,frame,l,mid,r);
    }


    /**
     * 优化后merge方法 不需要拷贝数组
     * @param frame
     * @param auxFrame
     * @param l
     * @param mid
     * @param r
     */
    private void merge(AlgoFrame frame, AlgoFrame auxFrame, int l, int mid, int r) {
        // 设置显示
        auxFrame.optimizeSetData(frame,l,l,-1,-1);
        quickMerge(auxFrame,frame,l,mid,r);
        // 更新数组排序区间
        auxFrame.optimizeSetData(frame,l,r+1,-1,-1);
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
     * 插入排序
     * @param frame
     * @param l
     * @param r
     */
    private void InsertSort(AlgoFrame auxFrame,AlgoFrame frame, int l, int r){
        for (int i = l; i <= r; i++) {
            // 假定[l,l+1]是有序的 则循环后面的元素找到他们在有序数组中的位置
            for (int j = i; j > l && frame.less(j,j - 1); j--) {
                auxFrame.optimizeSetData(frame,l,i+1,j,j-1);
                frame.swap(j,j - 1);
            }
        }
        auxFrame.optimizeSetData(frame,l,r + 1,-1,-1);
    }

    /**
     * 归并排序可视化
     * @param frame
     * @param l
     * @param r
     */
    private void merge(AlgoFrame frame, int l,int mid, int r) {
        // 将数据放入临时数组
        for (int k = l; k <= r; k++) {
            frame.replace(auxData,k,frame.get(k));
        }
        quickMerge(frame,auxData,l,mid,r);
        // 更新数组排序区间
        frame.setData(l,r+1,l,r);
    }

    /**
     * 快速归并
     */
    public void quickMerge(AlgoFrame frame,AlgoFrame auxData,int l,int mid,int r){
        int i = l,j = mid + 1;
        // 归并数组
        for (int k = l; k <= r; k++) {
            if (i > mid) auxData.replace(frame,k,auxData.get(j++));
            else if (j > r) auxData.replace(frame,k,auxData.get(i++));
            else if (auxData.less(i,j)) auxData.replace(frame,k,auxData.get(i++));
            else auxData.replace(frame,k,auxData.get(j++));
        }
    }

    @Override
    public String methodName() {
        return "Merge Sort";
    }

    /**
     * 自底而上的归并模式
     */
    class MergeBu implements SortMethod{
        @Override
        public void sort(AlgoFrame frame) {

        }

        /**
         * 自底而上的归并模式
         *  循序渐进的解决问题
         * @param arr
         * @return
         */
        @Override
        public int[] sort(int[] arr) {
            int n = arr.length;
            aux = new int[n];
            // i为子数组的大小
            for (int i = 1; i < n; i = i<<1)
                // j为子数组的索引
                for (int j = 0; j < n - i; j += i<<1)
                    // 归并子数组
                    merge(arr,j,j+i-1,Math.min(j+(i<<1)-1,n-1));
            return arr;
        }

        @Override
        public String methodName() {
            return "MergeBu Sort";
        }
    }


    public static void main(String[] args) {
        MergeBu mergeBu = new MergeSort().new MergeBu();
        long sort = mergeBu.testSort(mergeBu, 10000000);
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
