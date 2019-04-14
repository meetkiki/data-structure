/**
 * 索引优先队列
 */
public class IndexMinPQ<T extends Comparable<T>> extends MaxPQ<T> {

    /**
     * 索引
     */
    private int[] pq;
    /**
     * 索引逆序 qp[pq[i]] = pa[qp[i]] = i
     *  即用qp存储i在pq中的位置  索引j qp[j] = i
     */
    private int[] qp;
    /**
     * 创建一个索引优先队列，取值范围为0到n-1
     * @param n
     */
    public IndexMinPQ(int n) {
        this.pq = new int[n + 1];
        this.qp = new int[n + 1];
        this.data = (T[])new Comparable[n + 1];
        for (int i = 0; i <= n; i++) {
            qp[i] = -1;
        }
    }

    /**
     * 根据索引判断元素是否存在
     * @param k
     * @return
     */
    public boolean contains(int k){
        return qp[k] != -1;
    }

    /**
     * 在索引k处插入元素
     * @param k
     * @param t
     */
    public void insert(int k,T t){
        count++;
        // 索引数组更新
        pq[count] = k;
        // qp存储k在pq的位置
        qp[k] = count;
        // 在索引k处存储元素
        data[k] = t;
        // 上浮
        swim(count);
    }

    /**
     * 返回最小值
     * @return
     */
    public T min(){
        return data[pq[1]];
    }

    /**
     * 删除最小值 返回索引
     * @return
     */
    public int deleteMin(){
        int minI = pq[1];
        // 最后一个值覆盖最小值
        each(1,count--);
        // 调整位置
        sink(1);
        //
        data[pq[count + 1]] = null;
        qp[pq[count + 1]] = -1;
        return minI;
    }

    public void change(int k,T t){
        data[k] = t;
        // 调整位置 qp的位置即为原有k的值
        swim(qp[k]);
        sink(qp[k]);
    }


    public int minIndex(){
        return pq[1];
    }

    /**
     * 删除方法
     * @return
     */
    public void delete(int k){
        int retI = pq[k];
        // 最后一个值覆盖最小值
        each(retI,count--);
        // 调整位置
        sink(retI);
        swim(retI);
        data[k] = null;
        qp[k] = -1;
    }


    @Override
    protected boolean less(int i, int j) {
        return data[i].compareTo(data[j]) > 0;
    }

    @Override
    protected void each(int i, int j) {
        Comparable a = data[i];data[i] = data[j];data[j] = (T)a;
        int b = qp[i];qp[i] = qp[j];qp[j] = b;
        int c = pq[i];pq[i] = pq[j];pq[j] = c;
    }
}
