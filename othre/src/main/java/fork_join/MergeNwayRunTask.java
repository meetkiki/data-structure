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

    private SortArray aux;
    /**
     * 数据项
     */
    private SortArray data;
    private int l;
    private int r;
    private int n;

    public MergeNwayRunTask(){}

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

    public MergeNwayRunTask(SortArray data, SortArray aux, int l, int r) {
        this(data,aux,l,r,2);
    }

    public MergeNwayRunTask(SortArray data, SortArray aux, int l, int r, int n) {
        this.data = data;
        this.aux = aux;
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
        tasks.add(new MergeNwayRunTask(aux,data, l, l + mid,n));
        while (i < h) {
            tasks.add(new MergeNwayRunTask(aux,data, l + i * mid + 1,l + (++i) * mid, n));
        }
        tasks.add(new MergeNwayRunTask(aux,data,l + i * mid + 1,r,n));
//        MergeRunTask taskleft = new MergeRunTask(aux,data, l, mid);
//        MergeRunTask taskCenter = new MergeRunTask(aux,data, mid + 1, 2 * mid);
//        MergeRunTask taskright = new MergeRunTask(aux,data, 2 * mid + 1, r);
        // 执行子任务
        System.out.println("l=" + l + " ; r=" + r);
        System.out.println(tasks);
        invokeAll(tasks);
        //等待任务执行结束合并其结果
        for (MergeNwayRunTask task : tasks) {
            task.join();
        }
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

    @Override
    public String toString() {
        return "MergeRunTask{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }
}
