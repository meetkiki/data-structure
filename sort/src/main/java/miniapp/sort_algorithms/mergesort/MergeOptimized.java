package miniapp.sort_algorithms.mergesort;


import miniapp.abstraction.Constant;
import miniapp.abstraction.Optimized;
import miniapp.view.AlgoFrame;

public class MergeOptimized extends MergeFrame implements Optimized {

    /**
     * 优化接口实现
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr) {
        return super.sort(arr);
    }

    /**
     * 优化接口实现
     * @param frame
     */
    @Override
    public void optimizedSort(AlgoFrame frame) {
        int length = frame.length(),l = 0,r = length - 1;
        // 归并排序初始化
        AlgoFrame auxFrame = frame.cloneData();
        // 优化后归并
        optimizeMerge(auxFrame,frame,l,r);
    }

    /**
     * 归并排序改进后方法
     *    Merge Sort is 1.2 times faster than Shell
     * @param auxFrame  拷贝数组
     * @param frame     原数组
     * @param l
     * @param r
     */
    private void optimizeMerge(AlgoFrame auxFrame, AlgoFrame frame, int l, int r) {
        /**
         * 归并排序优化① 对于小规模数据使用插入排序
         */
        if (auxFrame.compareLessOrEqual(r - l, Constant.INSERTSIZE)){
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
            // auxFrame.dataCoppy(auxFrame,l,frame,l,r - l + 1);
            auxFrame.optimizeUpdateOrdered(frame,l,r + 1);
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
        auxFrame.optimizeUpdateOrdered(frame,l,l);
        quickMerge(auxFrame,frame,l,mid,r);
        // 更新数组排序区间
        auxFrame.optimizeUpdateOrdered(frame,l,r+1);
    }

}
