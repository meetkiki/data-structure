package fork_join;


import java.util.concurrent.RecursiveTask;

/**
 * @author ypt
 * @ClassName RunTask
 * @date 2019/4/2 15:17
 */
public class RunTask extends RecursiveTask {
    /**
     * 数组大小标准 如果小于这个数 就执行
     */
    private static final int threshold = 15;

    private SortArray aux;
    /**
     * 数据项
     */
    private SortArray data;
    private int l;
    private int r;

    public RunTask(){}

    public RunTask(SortArray data) {
        this.data = data;
        this.l = 0;
        this.r = data.getSize() - 1;
        int[] clone = data.getData().clone();
        aux = new SortArray(clone);
    }

    public RunTask(SortArray data,SortArray aux, int l, int r) {
        this.data = data;
        this.aux = aux;
        this.l = l;
        this.r = r;
    }


    public SortArray insertSort(SortArray data,int l,int r){
        for (int i = l + 1; i <= r; i++) {
            // 选择合适的位置
            int temp = data.get(i);
            int j = i;
            for (; j > l && temp<data.get(j-1); j--) {
                data.set(j,data.get(j-1));
            }
            data.set(j,temp);
        }
        return data;
    }

    @Override
    protected Object compute() {
        if (r - l <= threshold){
            return insertSort(data,l,r);
        }
        int mid = l + ((r - l)>>1);
        // 拆分子任务
        RunTask taskleft = new RunTask(data,aux, l, mid);
        RunTask taskright = new RunTask(data,aux,mid+1, r);
        // 执行子任务
        invokeAll(taskleft,taskright);
        //等待任务执行结束合并其结果
        taskleft.join();
        taskright.join();
        // 局部有序 不进行merge
        if (data.get(mid) <= data.get(mid+1)){
            return data;
        }
        // 合并结果
        merge(data,l,mid,r);
        return data;
    }

    /**
     * 归并结果
     * @param data
     * @param l
     * @param mid
     * @param r
     */
    private void merge(SortArray data, int l, int mid, int r) {
        int i = l,j = mid + 1;
        // 拷贝临时数组
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
}
