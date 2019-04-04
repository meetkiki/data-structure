package fork_join;

import java.util.concurrent.RecursiveAction;

/**
 * @author ypt
 * @ClassName QuickRunTask
 * @date 2019/4/3 9:55
 */
public final class QuickRunTask extends RecursiveAction {

    /**
     * 数组大小标准 如果小于这个数 就执行插入排序
     */
    private static final int threshold = 48;


    /**
     * 数据项
     */
    private final SortArray data;
    private final int l;
    private final int r;


    public QuickRunTask(SortArray data, int l, int r) {
        this.data = data;
        this.l = l;
        this.r = r;
    }



    @Override
    final protected void compute() {
        // 优化1 小数组使用插入排序
        if (r - l <= threshold){
            SortArray.insertSort(data,l,r);
            return;
        }
        // 优化2 三向切分快速排序
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
        QuickRunTask leftTask = new QuickRunTask(data, l, i - 1);
        QuickRunTask centerTask = new QuickRunTask(data, i + 1, j - 1);
        QuickRunTask rightTask = new QuickRunTask(data, j + 1, r);
        invokeAll(leftTask,centerTask,rightTask);
        leftTask.join();
        centerTask.join();
        rightTask.join();
    }
//    @Override
//    protected void compute() {
//        // 优化1 小数组使用插入排序
//        if (r - l <= threshold){
//            SortArray.insertSort(data,l,r);
//            return;
//        }
//        // 优化2 三向切分快速排序
//        int i = l,j = l + 1,k = r,v = data.get(l);
//        while (j <= k){
//            // 第一次用j与最左值比较
//            int compare = data.get(j) - v;
//            // 如果 data[j]小于选择值 与前面的值交换 i和j加一
//            if (compare < 0) data.swap(i++,j++);
//            // 如果 data[j]大于选择值 与选择值交换 k--
//            else if (compare > 0) data.swap(j, k--);
//            // 如果 data[j]选择值 j加一
//            else j++;
//        }
//        QuickRunTask leftTask = new QuickRunTask(data, l, i - 1);
//        QuickRunTask rightTask = new QuickRunTask(data, k + 1, r);
//        invokeAll(leftTask,rightTask);
//        leftTask.join();
//        rightTask.join();
//    }

}
