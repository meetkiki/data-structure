package miniapp.sort_algorithms.parallelsort.merge;

import miniapp.Enum.Constant;
import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author Tao
 */
public class ParallelMergeSort implements SortMethod {


    private static ForkJoinPool forkJoinPool;

    public ParallelMergeSort(){
        forkJoinPool = new ForkJoinPool();
    }

    @Override
    public int[] sort(int[] arr) {
        try {
            RunTask runTask = new RunTask(arr);
            forkJoinPool.submit(runTask).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    class RunTask extends RecursiveAction{
        /**
         * 临时数组
         */
        protected DataValue dataThread;

        public RunTask(DataValue dataThread) {
            this.dataThread = dataThread;
        }
        public RunTask(int[] arr) {
            this.dataThread = new DataValue(arr,arr.clone(),0,arr.length - 1);
        }
        @Override
        protected void compute() {
            DataValue value = dataThread;
            // 优化1 小数组插入排序
            if (value.r - value.l <= Constant.INSERTSIZE){
                insertSort(value.data,value.l,value.r);
                return;
            }
            int mid = value.l + ((value.r - value.l)>>1);
            // 拆分子任务 //优化2 在子任务中交换源数组和临时数组的角色优化拷贝
            RunTask taskleft = new RunTask(new DataValue(value.aux,value.data, value.l, mid));
            RunTask taskright = new RunTask(new DataValue(value.aux,value.data,mid + 1, value.r));
            // 执行子任务
            invokeAll(taskleft,taskright);
            //等待任务执行结束合并其结果
            taskleft.join();
            taskright.join();
            // 优化3 局部有序 不进行merge
            if (value.aux[mid] <= value.aux[mid+1]){
                // 有序 拷贝数组
                System.arraycopy(value.aux,value.l,value.data,value.l,(value.r - value.l + 1));
                return;
            }
            // 合并结果
            merge(value.data,value.aux,value.l,mid,value.r);
            // 释放空间
            taskleft.destroy();
            taskright.destroy();
            return;
        }

        public void destroy(){
            this.dataThread.destroy();
        }

        private void merge(int[] data, int[] aux, int l, int mid, int r) {
            int i = l,j = mid + 1;
            // 归并
            for (int k = l; k <= r; k++) {
                if (i > mid) data[k] = aux[j++];
                else if(j > r) data[k] = aux[i++];
                else if(aux[i]< aux[j]) data[k] = aux[i++];
                else data[k] = aux[j++];
            }
        }
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Gold;
    }

    @Override
    public String getCnName() {
        return "并行归并排序";
    }

    @Override
    public String methodName() {
        return "ParallelMergeSort";
    }

    @Override
    public void destory() {
    }
}
