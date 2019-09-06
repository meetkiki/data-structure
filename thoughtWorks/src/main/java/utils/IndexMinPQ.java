package utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 索引优先队列
 *
 *  优先队列的缺点是不能随机支持访问并更新他们
 *  1.索引优先队列的目的则是通过维护pq索引数组来达到实现优先队列，而data数组的值则不用改变
 *
 * @author Tao
 */
public class IndexMinPQ<K,T extends Comparable<T>> {

    /**
     * 索引
     */
    private int[] pq;
    /**
     * 索引的key 这里原本是int索引 这里增强保存key对应的int索引位置
     */
    private Map<K,Integer> pqkey;
    /**
     * 索引逆序 qp[pq[i]] = pa[qp[i]] = i
     *  即用qp存储i在pq中的位置  索引j qp[j] = i
     * 维护qp数组的原因是pq维护了data的优先队列，
     *  而原生的k则需要重新遍历数组，所以这里需要用到索引逆序快速找到整数k的值在pq的位置
     */
    private int[] qp;
    /**
     * 数据存储
     */
    protected T[] data;
    /**
     * 元素数
     */
    protected int count;
    /**
     * 创建一个索引优先队列，取值范围为0到n-1
     * @param n
     */
    public IndexMinPQ(int n) {
        this.pq = new int[n + 1];
        this.qp = new int[n + 1];
        this.pqkey = new ConcurrentHashMap<>();
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
    public boolean contains(K k){
        return pqkey.containsKey(k);
    }

    /**
     * 在索引k处插入元素
     * @param k
     * @param t
     */
    public void insert(K k,T t){
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

    /**
     * 比较方法，判断当前数据是否满足
     * @param i
     * @param j
     * @return
     */
    protected boolean less(int i, int j) {
        return data[pq[i]].compareTo(data[pq[j]]) > 0;
    }

    /**
     * 维护pq和qp数组的值
     * @param i
     * @param j
     */
    protected void each(int i, int j) {
        int a = pq[i];pq[i] = pq[j];pq[j] = a;
        int b = qp[i];qp[i] = qp[j];qp[j] = b;
    }

    /**
     * 当一个元素比他的父节点大时我们需要上浮 直到它不大于父节点为止
     *  我们可以将它与父节点交换（交换后 它比这两个子节点都大 因为另外一个是父节点 的子节点 一定是比父节点小）
     * @param k
     */
    protected void swim(int k){
        // 直到k不大于父节点结束循环
        while (k > 1 && less((k >> 1),k)){
            // 交换父节点和当前节点的值
            each(k/2,k);
            k = (k >> 1);
        }
    }

    /**
     * 当堆的当前节点小于子节点时需要下沉
     *  这里我们需要交换父节点和子节点中的较小者
     * @param k
     */
    protected void sink(int k){
        // 直到k下沉到叶子结点结束
        int i;
        while ((i = (k << 1)) <= count){
            // 如果右子结点比左子节点大 则交换元素为右子节点
            if (i < count && less(i,i + 1)) i++;
            // 如果k比i小 则交换
            if (less(k,i)){
                each(k,i);
                k = i;
            }else{
                // 否则终止循环
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 1; i <= count; i++) {
            buffer.append(data[pq[i]].toString());
            if (i != count){
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }


}
