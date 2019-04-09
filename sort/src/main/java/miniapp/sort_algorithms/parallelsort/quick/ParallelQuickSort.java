package miniapp.sort_algorithms.parallelsort.quick;

import miniapp.Enum.Constant;
import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;
import miniapp.sort_algorithms.parallelsort.merge.DataValue;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author Tao
 */
public class ParallelQuickSort implements SortMethod {

    private static ForkJoinPool forkJoinPool;

    public ParallelQuickSort(){
        forkJoinPool = new ForkJoinPool();
    }

    @Override
    public int[] sort(int[] arr) {
        try {
            QuickTask runTask = new QuickTask(arr);
            forkJoinPool.submit(runTask).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Yellow;
    }

    @Override
    public String methodName() {
        return "ParallelQuickSort";
    }

    @Override
    public String getCnName() {
        return "并行快速排序";
    }

    @Override
    public void destory() {
    }

    class QuickTask extends RecursiveAction{

        /**
         * 临时数组
         */
        protected DataValue dataThread;

        public QuickTask(DataValue dataThread) {
            this.dataThread = dataThread;
        }

        public QuickTask(int[] arr) {
            this.dataThread = new DataValue(arr,arr.clone(),0,arr.length - 1);
        }

        @Override
        protected void compute() {
            DataValue value = dataThread;
            // 优化1 小数组使用插入排序
            if (value.r - value.l <= Constant.INSERTSIZE){
                insertSort(value.data,value.l,value.r);
                return;
            }
            // 优化2 三向切分快速排序
            int i = value.l,j = value.l + 1,k = value.r,v = value.data[value.l];
            while (j <= k){
                // 第一次用j与最左值比较
                int compare = value.data[j] - v;
                // 如果 data[j]小于选择值 与前面的值交换 i和j加一
                if (compare < 0) swap(value.data,i++,j++);
                    // 如果 data[j]大于选择值 与选择值交换 k--
                else if (compare > 0) swap(value.data,j, k--);
                    // 如果 data[j]选择值 j加一
                else j++;
            }
            QuickTask leftTask = new QuickTask(new DataValue(value.data, value.l, i - 1));
            QuickTask rightTask = new QuickTask(new DataValue(value.data, k + 1, value.r));
            invokeAll(leftTask,rightTask);
            leftTask.join();
            rightTask.join();
            // 释放内存
            leftTask.destroy();
            rightTask.destroy();
            return;
        }

        public void destroy(){
            this.dataThread.destroy();
        }
    }

}
