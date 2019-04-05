package miniapp.sort_algorithms.mergesort;

import miniapp.abstraction.SortVisual;
import miniapp.view.AlgoFrame;

public class MergeFrame extends MergeSort implements SortVisual {

    /**
     * 临时数组
     */
    public static AlgoFrame auxData;

    @Override
    public void sort(AlgoFrame frame) {
        int length = frame.length(),l = 0,r = length - 1;
        auxData = frame.cloneData();
        // 初始归并
        mergesort(frame,l,r);
    }

    /**
     * 归并排序可视化
     * @param frame
     * @param l
     * @param r
     */
    protected void mergesort(AlgoFrame frame, int l, int r) {
        if (frame.compareLessOrEqual(r, l)) return;
        int mid = l + ((r - l) >> 1);
        // 分治归并
        mergesort(frame,l,mid);
        mergesort(frame,mid + 1,r);
        // 合并两个数组
        merge(frame, l, mid ,r);
    }

    /**
     * 归并排序可视化
     *  需要拷贝版
     * @param frame
     * @param l
     * @param r
     */
    protected void merge(AlgoFrame frame, int l,int mid, int r) {
        // 将数据放入临时数组
        for (int k = l; frame.compareLessOrEqual(k, r); k++) {
            frame.replace(auxData,k,frame.get(k));
        }
        // 快速归并
        quickMerge(frame,auxData,l,mid,r);
        // 更新数组排序区间
        frame.updateOrdereds(l,r+1);
    }

    /**
     * 插入排序
     * @param frame
     * @param l
     * @param r
     */
    static void InsertSort(AlgoFrame auxFrame, AlgoFrame frame, int l, int r){
        for (int i = l;auxFrame.compareLessOrEqual(i, r); i++) {
            // 假定[l,l+1]是有序的 则循环后面的元素找到他们在有序数组中的位置
            for (int j = i; auxFrame.compareMore(j, l) && frame.less(j,j - 1); j--) {
                frame.swap(j,j - 1);
            }
            auxFrame.optimizeUpdateOrdered(frame,l,i+1);
        }
        auxFrame.optimizeUpdateOrdered(frame,l,r + 1);
    }


    /**
     * 快速归并
     *  不需要拷贝
     *  frame   源数组
     *  auxData 临时数组
     *
     */
    static void quickMerge(AlgoFrame frame,AlgoFrame auxData,int l,int mid,int r){
        int i = l,j = mid + 1;
        // 归并数组
        for (int k = l; auxData.compareLessOrEqual(k, r); k++) {
            if (auxData.compareMore(i, mid)) auxData.replace(frame,k,auxData.get(j++));
            else if (auxData.compareMore(j, r)) auxData.replace(frame,k,auxData.get(i++));
            else if (auxData.less(i,j)) auxData.replace(frame,k,auxData.get(i++));
            else auxData.replace(frame,k,auxData.get(j++));
        }
    }

    @Override
    public String methodName() {
        return "merge default sort";
    }
}
