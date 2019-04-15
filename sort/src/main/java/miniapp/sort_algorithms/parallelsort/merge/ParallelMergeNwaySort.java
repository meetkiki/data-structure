package miniapp.sort_algorithms.parallelsort.merge;

import miniapp.Enum.Constant;
import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author Tao
 */
public class ParallelMergeNwaySort implements SortMethod {
    /**
     * 多线程排序必须要自己的线程池 否则效率不精确
     */
    private static ForkJoinPool forkJoinPool;
    /**
     * 多路归并排序
     */
    private int N = Runtime.getRuntime().availableProcessors();

    public ParallelMergeNwaySort() {
        forkJoinPool = new ForkJoinPool();
    }

    @Override
    public int[] sort(int[] arr) {
        try {
            RunNwayTask runTask = new RunNwayTask(arr,N);
            forkJoinPool.submit(runTask).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.HotPink;
    }

    @Override
    public String methodName() {
        return "ParallelMergeNwaySort";
    }

    @Override
    public String getCnName() {
        return "并行N路归并排序";
    }

    @Override
    public void destory() {
    }

    class RunNwayTask extends RecursiveAction {
        /**
         * 临时数组
         */
        protected final DataValue dataThread;

        public RunNwayTask(DataValue dataThread) {
            this.dataThread = dataThread;
        }

        public RunNwayTask(int[] arr, int n) {
            this.dataThread = new DataValue(arr,arr.clone(),0,arr.length - 1,n);
        }
        @Override
        protected void compute() {
            DataValue value = this.dataThread;
            // 优化1 小数组插入排序
            if (value.r - value.l <= Constant.INSERTSIZE){
                insertSort(value.data,value.l,value.r);
                return;
            }
            // 拆分子任务
            //存储N个数组的下标起始位置和终止位置
            List<RunNwayTask> tasks = new ArrayList<>(16);
            int[][] pos = new int[value.n][2];
            int mid = (value.r - value.l) / value.n,h = 0;
            // 起始
            for (pos[h++][0] = value.l; h < value.n;h++) {
                pos[(h - 1)][1] = value.l + h * mid;
                //优化2 在子任务中交换源数组和临时数组的角色优化拷贝
                tasks.add(new RunNwayTask(new DataValue(value.aux,value.data, pos[(h - 1)][0],pos[(h - 1)][1],value.n)));
                pos[h][0] = value.l + h * mid + 1;
            }
            // 终止
            pos[(h - 1)][1] = value.r;
            tasks.add(new RunNwayTask(new DataValue(value.aux,value.data,pos[(h - 1)][0],pos[(h - 1)][1],value.n)));
            // 执行子任务
            invokeAll(tasks);
            //等待任务执行结束合并其结果
            tasks.forEach((t)->t.join());
            // 判断是否已经有序 如果有序不进行merge
            boolean isSort = checkSorted(pos,value.aux);
            if (isSort){
                System.arraycopy(value.aux,value.l,value.data,value.l,(value.r - value.l + 1));
                return;
            }
            // 合并N个数组结果
            mergeNway(value.data,value.aux,value.l,pos,value.r);
            // 释放内存
            tasks.forEach((t)->t.destroy());
            return;
        }

        /**
         * 归并结果
         *
         *  这个操作会影响pos数组的值
         * @param desc 数组
         * @param l    左下标
         * @param pos  下标
         * @param r    右下标
         */
        private void mergeNway(int[] desc,int[] aux,int l, int[][] pos, int r) {
            // 在子递归中交换角色 优化拷贝数组
            // 归并 N个数组
            IndexMinPQ minPQ = new IndexMinPQ(r - l + 1);
            // 读取数组的第一个数据
            for (int i = 0; i < pos.length; i++) {
                // 数据项还有值
                if(pos[i][0] <= pos[i][1]){
                    minPQ.insert(i,aux[pos[i][0]++]);
                }
            }
            int i = l;
            // 如果不为空则继续读取合并
            while (!minPQ.isEmpty()){
                int min = minPQ.min();
                desc[i++] = min;
                // 得到最小数组组的索引 (第index组数组)
                int index = minPQ.deleteMin();
                // 如果还有值 继续入队
                if (pos[index][0] <= pos[index][1]){
                    minPQ.insert(index,aux[pos[index][0]++]);
                }
            }
        }

        /**
         * 如果数组的位置有序不进行merge
         * @param pos
         * @param aux
         * @return
         */
        private boolean checkSorted(int[][] pos, int[] aux) {
            for (int i = 1; i < pos.length; i++) {
                if (aux[pos[i][0]] < aux[pos[i - 1][1]]){
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
        private int choseMin(int[][] pos,int[] aux) {
            int i1,i2,i3 = -1,min = Integer.MAX_VALUE;
            for (int i = 0; i < pos.length; i++) {
                if ((i1 = pos[i][0]) > pos[i][1]){
                    continue;
                }
                // 选取最小值
                if ((i2 = aux[i1]) < min){
                    min = i2;
                    i3 = i;
                }
            }
            return pos[i3][0]++;
        }

        public void destroy(){
            this.dataThread.destroy();
        }
    }

    /**
     * 索引优先队列
     */
    private class IndexMinPQ{
        // 用于存储data的索引k
        private int[] pq;
        // 用于存储pq的反序 即存储k在pq中的位置 pq[i] = k qp[k] = i
        private int[] qp;
        // 数据项
        private int[] data;
        // 计数
        private int count;
        // 构造函数
        public IndexMinPQ(int N) {
            this.pq = new int[N + 1];
            this.qp = new int[N + 1];
            this.data = new int[N + 1];
            for (int i = 0; i < qp.length; i++) {
                qp[i] = -1;
            }
        }
        /**  上浮 即子节点的值比父节点小时*/
        private void swim(int i){
            while (i > 1 && more(i >> 1,i)){
                swap(i >> 1,i);
                i = i >> 1;
            }
        }
        /** 下沉 即父节点的值比子节点大时 */
        private void sink(int i){
            while ((i<<1) <= count){
                int k = i << 1;
                // 找出较小的儿子
                if (k < count && more(k,k+1)) k++;
                // 如果儿子小于父亲
                if (more(i,k)){
                    swap(i,k);
                    i = k;
                }else{
                    break;
                }
            }
        }
        /**
         * 比较数据 这里实现的是pq数组的优先队列 所以使用pq的值在data中判断大小
         */
        private boolean more(int i, int j){
            return data[pq[i]] > data[pq[j]];
        }
        /**
         * 交换数据 这里不用交换data的数据项 只需要维护pq和qp即可
         */
        private void swap(int i,int j){
            int a = pq[i];pq[i] = pq[j];pq[j] = a;
            int b = qp[i];qp[i] = qp[j];qp[j] = b;
        }

        /**
         * 在索引k处插入值t
         * @param k
         * @param t
         */
        public void insert(int k,int t){
            // 关联整数k可以取0
            k++;
            data[++count] = t;
            pq[k] = count;
            qp[count] = k;
            swim(k);
        }

        /**
         * 返回最小值
         * @return
         */
        public int min(){
            return data[pq[1]];
        }

        /**
         * 删除最小值 返回索引
         * @return
         */
        public int deleteMin(){
            int minIndex = pq[1];
            swap(count--,1);
            // 下沉
            sink(1);
            // 将索引值置空 pq[count]的值是qp数组的索引
            qp[pq[count + 1]] = -1;
            return --minIndex;
        }

        /**
         * 是否为空
         * @return
         */
        public boolean isEmpty(){
            return count == 0;
        }
    }

    public static void main(String[] args) {
        ParallelMergeNwaySort parallelMergeNwaySort = new ParallelMergeNwaySort();
        long sort = parallelMergeNwaySort.testSort(500000);
        System.out.println("花费时间"+sort+"ms");
    }

}
