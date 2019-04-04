package fork_join;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author ypt
 * @ClassName MergeRunTask
 * @date 2019/4/2 15:17
 */
public final class MergeNwayRunTask extends RecursiveTask {
    /**
     * 数组大小标准 如果小于这个数 就执行插入排序
     */
    private static final int threshold = 48;

    private static SortArray aux;
    /**
     * 数据项
     */
    private SortArray data;
    private int l;
    private int r;
    private int n;

    public MergeNwayRunTask(SortArray data) {
        this(data,2);
    }

    public MergeNwayRunTask(SortArray data, int n) {
        this.data = data;
        this.l = 0;
        this.r = data.getSize() - 1;
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
    protected Object compute() {
        // 优化1 小数组插入排序
        if (r - l <= threshold){
            return SortArray.insertSort(data,l,r);
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
        // 合并结果mid + l + 1
        merge(data,l,l + mid,l + 2 * mid);
        merge(data,l,l + 2 * mid,r);
        return data;
    }

    /**
     * 归并结果
     * @param data
     * @param l
     * @param mid
     * @param r
     */
    private void merge(SortArray data,int l, int mid, int r) {
        int i = l,j = mid + 1;
        // 拷贝数组
        for (int k = l; k <= r; k++) {
            aux.set(k,data.get(k));
        }
        // 归并
        for (int k = l; k <= r; k++) {
            if (i > mid) data.set(k,aux.get(j++));
            else if(j > r) data.set(k,aux.get(i++));
            else if(aux.less(i,j)) data.set(k,aux.get(i++));
            else data.set(k,aux.get(j++));
        }
    }

    @Override
    public String toString() {
        return "MergeRunTask{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }
}
