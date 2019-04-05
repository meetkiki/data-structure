package forkjoin.mergesort;


import forkjoin.AbstractRunTask;
import forkjoin.SortArray;

/**
 * @author ypt
 * @ClassName MergeRunTask
 * @date 2019/4/2 15:17
 */
public final class MergeRunTask extends AbstractRunTask {

    private SortArray aux;
    private int n;

    public MergeRunTask(){}

    public MergeRunTask(SortArray data) {
        this(data,new SortArray(data.getData().clone()),0,data.size() - 1);
    }

    public MergeRunTask(SortArray data, SortArray aux, int l, int r) {
        this.data = data;
        this.aux = aux;
        this.l = l;
        this.r = r;
    }



    @Override
    protected Object compute() {
        // 优化1 小数组插入排序
        if (r - l <= threshold){
            return SortArray.insertSort(data,l,r);
        }
        int mid = l + ((r - l)>>1);
        // 拆分子任务 //优化2 在子任务中交换源数组和临时数组的角色优化拷贝
        MergeRunTask taskleft = new MergeRunTask(aux,data, l, mid);
        MergeRunTask taskright = new MergeRunTask(aux,data,mid + 1, r);
        // 执行子任务
        invokeAll(taskleft,taskright);
        //等待任务执行结束合并其结果
        taskleft.join();
        taskright.join();
        // 优化3 局部有序 不进行merge
        if (aux.get(mid) <= aux.get(mid+1)){
            // 有序 拷贝数组
            SortArray.arrayCoppy(aux,l,data,l,(r - l + 1));
            return data;
        }
        // 合并结果
        merge(data,aux,l,mid,r);
        return data;
    }

    /**
     * 归并结果
     * @param data
     * @param l
     * @param mid
     * @param r
     */
    private void merge(SortArray data,SortArray aux, int l, int mid, int r) {
        int i = l,j = mid + 1;
        // 归并
        for (int k = l; k <= r; k++) {
            if (i > mid) data.set(k,aux.get(j++));
            else if(j > r) data.set(k,aux.get(i++));
            else if(aux.less(i,j)) data.set(k,aux.get(i++));
            else data.set(k,aux.get(j++));
        }
    }

}
