package fork_join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @author ypt
 * @ClassName QuickRunTask
 * @date 2019/4/3 9:55
 */
public class QuickRunTask extends RecursiveAction {

    /**
     * 数组大小标准 如果小于这个数 就执行插入排序
     */
    private static final int threshold = 15;


    /**
     * 数据项
     */
    private volatile SortArray data;
    private int l;
    private int r;

    public QuickRunTask(SortArray data) {
        this.data = data;
        this.l = 0;
        this.r = data.getSize() - 1;
    }

    public QuickRunTask(SortArray data, int l, int r) {
        this.data = data;
        this.l = l;
        this.r = r;
    }



    @Override
    protected void compute() {
        // 优化1 小数组使用插入排序
        if (r - l <= threshold){
            SortArray.insertSort(data,l,r);
            return;
        }
        // 优化2 三向切分快速排序
        int i = l,j = l + 1,k = r,v = data.get(l);
        while (j <= k){
            // 第一次用j与最左值比较
            int compare = data.get(j) - v;
            // 如果 data[j]小于选择值 与前面的值交换 i和j加一
            if (compare < 0) data.swap(i++,j++);
            // 如果 data[j]大于选择值 与选择值交换 k--
            else if (compare > 0) data.swap(j, k--);
            // 如果 data[j]选择值 j加一
            else j++;
        }
        QuickRunTask leftTask = new QuickRunTask(data, l, i - 1);
        QuickRunTask rightTask = new QuickRunTask(data, k + 1, r);
        invokeAll(leftTask,rightTask);
        leftTask.join();
        rightTask.join();
    }

}
