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

    private final SortArray aux;
    private final int n;

    public MergeNwayRunTask(SortArray data) {
        this(data,2);
    }

    public MergeNwayRunTask(SortArray data, int n) {
        this(data,new SortArray(data.getData().clone()),0,data.size() - 1,n);
    }

    public MergeNwayRunTask(SortArray data,SortArray aux, int l, int r) {
        this(data,aux,l,r,2);
    }

    public MergeNwayRunTask(SortArray data,SortArray aux, int l, int r, int n) {
        this.data = data;
        this.aux = aux;
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
        // 拆分子任务
        //存储N个数组的下标起始位置和终止位置
        int[][] pos = new int[n][2];
        List<MergeNwayRunTask> tasks = new ArrayList<>();
        int mid = (r - l) / n,h = 0;
        // 起始
        for (pos[h++][0] = l; h < n;h++) {
            pos[(h - 1)][1] = l + h * mid;
            //优化2 在子任务中交换源数组和临时数组的角色优化拷贝
            tasks.add(new MergeNwayRunTask(aux,data, pos[(h - 1)][0],pos[(h - 1)][1],n));
            pos[h][0] = l + h * mid + 1;
        }
        // 终止
        pos[(h - 1)][1] = r;
        tasks.add(new MergeNwayRunTask(aux,data,pos[(h - 1)][0],pos[(h - 1)][1],n));
        // 执行子任务
        invokeAll(tasks);
        //等待任务执行结束合并其结果
        for (MergeNwayRunTask task : tasks) {
            task.join();
        }
        // 判断是否已经有序 如果有序不进行merge
        boolean isSort = checkSorted(pos,aux);
        if (isSort){
            data.arrayCoppy(aux,l,data,l,(r - l + 1));
            return data;
        }
        // 合并N个数组结果
        mergeNway(data,aux,l,pos,r);
        return data;
    }

    /**
     * 归并结果
     *
     *  这个操作会影响pos数组的值
     * @param data 数组
     * @param l    左下标
     * @param pos  下标
     * @param r    右下标
     */
    private void mergeNway(SortArray data,SortArray aux,int l, int[][] pos, int r) {
        // 在子递归中交换角色 优化拷贝数组
        // 归并 N个数组
        for (int k = l; k <= r; k++) {
            // 选择N个数组的最小值
            int st = choseMin(pos,aux);
            data.set(k,aux.get(st));
        }
    }

    /**
     * 如果数组的位置有序不进行merge
     * @param pos
     * @param aux
     * @return
     */
    private boolean checkSorted(int[][] pos, SortArray aux) {
        for (int i = 1; i < pos.length; i++) {
            if (aux.get(pos[i][0]) < aux.get(pos[i - 1][1])){
                return false;
            }
        }
        return true;
    }

    /**
     * 选择子数组中的最小值
     * @param pos
     * @param aux
     * @return
     */
    private int choseMin(int[][] pos,SortArray aux) {
        int i1,i2,i3 = -1,min = Integer.MAX_VALUE;
        for (int i = 0; i < pos.length; i++) {
            if ((i1 = pos[i][0]) > pos[i][1]){
                continue;
            }
            // 选取最小值
            if ((i2 = aux.get(i1)) < min){
                min = i2;
                i3 = i;
            }
        }
        return pos[i3][0]++;
    }

    /**
     * 拆分数组的左右上限
     * @param l     左指针
     * @param mid   增量
     * @param r     右指针
     * @param n     n个数组
     * @return
     */
    @Deprecated
    private int[][] splitPos(int l, int mid, int r,int n){
        int h = 0;
        // 存储N个数组的下标起始位置和终止位置
        int[][] ll = new int[n][2];
        // 起始
        ll[h++][0] = l;
        while (h < n){
            ll[(h - 1)][1] = l + h * mid;
            ll[h][0] = l + (h++) * mid + 1;
        }
        // 终止
        ll[(h - 1)][1] = r;
        return ll;
    }


    @Override
    public String toString() {
        return "MergeRunTask{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }
}
