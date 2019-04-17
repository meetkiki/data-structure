
/**
 * 使用堆来实现一个有序队列
 *  二叉堆是一组能够用堆有序的完全二叉树排序的元素，并且在数组中按照层级存储（不使用数组的第一个位置）
 *
 *  在一个堆中，位置k的结点的父节点位置为k/2，而它的两个子节点为2k和2k+1
 * @author Tao
 * @param <T>
 */
public class MaxPQ<T extends Comparable<T>> {

    protected T[] data;
    protected int count;

    MaxPQ(){
        this(10);
    }
    MaxPQ(int size){
        this((T[])new Comparable[size + 1]);
    }
    MaxPQ(T[] arr){
        this.data = arr;
        this.count = 0;
    }

    /**
     * 插入一个元素
     * @param t
     */
    public void insert(T t){
        if ((count + 1) == data.length){
            resize(count<<1);
        }
        // 插入到叶子结点的位置
        data[++count] = t;
        // 上浮
        swim(count);
    }

    /**
     * 扩容
     */
    protected void resize(int n) {
        T[] temp = (T[])new Comparable[n + 1];
        int min = Math.min(n, count);
        for (int i = 1; i <= min; i++) {
            temp[i] = data[i];
            data[i] = null;
        }
        data = temp;
    }

    /**
     * 返回一个最大元素
     * @return
     */
    public T max(){
        return data[1];
    }

    /**
     * 删除一个最大元素
     * @return
     */
    public T deleteMax(){
        if (count < 1) return null;
        // 返回根节点
        T ret = data[1];
        // 将最后一个元素放到根节点的位置 这样根节点是比较小的元素 需要下沉
        each(count--,1);
        // 将数组中的最后一个元素释放
        data[count+1] = null;
        // 下沉
        sink(1);
        if (count <= (data.length >> 2) && data.length > 1){
            resize(data.length >> 1);
        }
        return ret;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return count == 0;
    }

    /**
     * 大小
     * @return
     */
    public int size(){
        return count;
    }

    /**
     * 小于
     * @param i
     * @param j
     * @return
     */
    protected boolean less(int i,int j){
        return data[i].compareTo(data[j]) < 0;
    }

    /**
     * 交换两个数据 的值
     */
    protected void each(int i,int j){
        Comparable a = data[i];data[i] = data[j];data[j] = (T)a;
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
            buffer.append(data[i].toString());
            if (i != count){
                buffer.append(" ,");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
