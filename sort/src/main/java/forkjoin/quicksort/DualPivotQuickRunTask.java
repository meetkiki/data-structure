package forkjoin.quicksort;

import forkjoin.AbstractRunTask;
import forkjoin.SortArray;

public class DualPivotQuickRunTask extends AbstractRunTask {


    public DualPivotQuickRunTask(SortArray data){
        this(data,0,data.size() - 1);
    }

    public DualPivotQuickRunTask(SortArray data, int l, int r) {
        this.data = data;
        this.l = l;
        this.r = r;
    }

    @Override
    final protected SortArray compute() {
        // 优化1 小数组使用插入排序
        if (r - l <= threshold){
            return data.insertSort(l,r);
        }
        // 优化2 双轴四项切分
        // 保证pivot1 小于等于pivot2
        if (data.less(r,l)){
            data.swap(l,r);
        }
        int i = l,j = r,k = l + 1,pivot1 =data.get(l),pivot2 = data.get(r);
        OUT_LOOP:while (k < j){
            if (data.get(k) < pivot1){
                //i先增加，k扫描中pivot1就不参与其中
                data.swap(++i,k++);
            }else if (data.get(k) >= pivot1 && data.get(k) <= pivot2){
                k++;
            }else{
                while (data.get(--j) > pivot2) {
                    if (j <= k) {
                        // 扫描终止
                        break OUT_LOOP;
                    }
                }
                // A[j] 大于等于pivot1 && A[j] 小于等于pivot2
                if (data.get(j) < pivot1) {
                    data.swap(j, k);
                    data.swap(++i, k++);
                } else {
                    data.swap(j, k++);
                }
            }
        }
        data.swap(l, i);
        data.swap(r, j);
        DualPivotQuickRunTask leftTask = new DualPivotQuickRunTask(data, l, i - 1);
        DualPivotQuickRunTask centerTask = new DualPivotQuickRunTask(data, i + 1, j - 1);
        DualPivotQuickRunTask rightTask = new DualPivotQuickRunTask(data, j + 1, r);
        invokeAll(leftTask,centerTask,rightTask);
        leftTask.join();
        centerTask.join();
        rightTask.join();
        return data;
    }
}
