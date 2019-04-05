package forkjoin.mergesort;


import forkjoin.AbstractRunTask;
import forkjoin.SortArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ypt
 * @ClassName MergeRunTask
 * @date 2019/4/2 15:17
 */
public final class MergeNwayRunTask extends AbstractRunTask {

    private static SortArray aux;
    private int n;

    public MergeNwayRunTask(SortArray data) {
        this(data,2);
    }

    public MergeNwayRunTask(SortArray data, int n) {
        this.data = data;
        this.l = 0;
        this.r = data.size() - 1;
        int[] clone = data.getData().clone();
        aux = new SortArray(clone);
        this.n = n;
    }

    public MergeNwayRunTask(SortArray data, int l, int r) {
        this(data,l,r,2);
    }

    public MergeNwayRunTask(SortArray data, int l, int r, int n) {
        this.data = data;
        this.l = l;
        this.r = r;
        this.n = n;
    }



    @Override
    protected SortArray compute() {
        // 优化1 小数组插入排序
        if (r - l <= threshold){
            return data.insertSort(l,r);
        }
        // 拆分子任务 //优化2 在子任务中交换源数组和临时数组的角色优化拷贝
        List<MergeNwayRunTask> tasks = new ArrayList<>();
        int mid = (r - l) / n,i = 1,h = n - 1;
        tasks.add(new MergeNwayRunTask(data, l,l + mid,n));
        while (i < h) {
            tasks.add(new MergeNwayRunTask(data,l + i * mid + 1,l + (++i) * mid, n));
        }
        tasks.add(new MergeNwayRunTask(data,l + i * mid + 1,r,n));
        // 执行子任务
        invokeAll(tasks);
        //等待任务执行结束合并其结果
        for (MergeNwayRunTask task : tasks) {
            task.join();
        }
        // 合并N个数组结果
        mergeNway(data,l,mid,r);
        return data;
    }

    /**
     * 归并结果
     * @param data 数组
     * @param l    左下标
     * @param mid  增量
     * @param r    右下标
     */
    private void mergeNway(SortArray data,int l, int mid, int r) {
        int i = l,j = mid + 1;
        // 拷贝数组
        for (int k = l; k <= r; k++) {
            aux.set(k,data.get(k));
        }
        // 归并 TODO

    }

    @Override
    public String toString() {
        return "MergeRunTask{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }
}
