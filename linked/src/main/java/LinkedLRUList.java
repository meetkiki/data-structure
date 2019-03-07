/**
 * 用链表实现LRU算法
 * @param <T>
 */
public class LinkedLRUList<T> extends LinkedList<T>{

    private int cacheSize;

    private int count;

    LinkedLRUList(){
        this(10);
    }

    LinkedLRUList(int cacheSize){
        this.cacheSize = cacheSize;
        this.count = 0;
    }

    /**
     * 新增的时候判断是否是否存在
     *
     *      1.不存在则判断元素大小
     *          如果大于最大元素个数 则删除尾部
     *          新增到头部
     *      2.存在则删除 新增到头部
     *
     * @param t
     */
    @Override
    public void add(T t) {
        Node pre = findNodePre(t);
        if (pre != null){
            pre.next = pre.next.next;
        }else{
            if (count == cacheSize) {
                //删除尾部
                deleteLast();
            }
        }
        // 增头
        Node nNode = new Node<>(t);
        nNode.next = head;
        head = nNode;
        count++;
    }

    /**
     * 查找元素的前置节点
     * @param t
     */
    public Node findNodePre(T t) {
        if (head == null || head.next == null){
            return null;
        }
        Node temp = head;
        while (temp.next != null){
            if (t.equals(temp.next.data)){
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * 删除最后一个元素
     */
    public void deleteLast(){
        if (head == null){
            return;
        }
        if (head.next == null){
            head = null;
            return;
        }
        //查找倒数第二个元素
        Node temp = head;
        while (temp.next.next != null){
            temp = temp.next;
        }
        temp.next = null;
        count --;
    }

}
