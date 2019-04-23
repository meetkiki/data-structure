import java.util.Iterator;
import java.util.ListIterator;

/**
 * 基于双向链表实现的背包
 * @param <T>
 */
public class Bag<T> implements Iterable<T>{

    private Node<T> head;

    /**
     * 插入一个元素
     * @param t
     */
    public void add(T t){
        Node oldNode = head;
        Node<T> node = new Node<>(t);
        node.next = oldNode;
        oldNode.parent = node;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>() {

            Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T t = current.t;
                current = current.next;
                return t;
            }

            @Override
            public boolean hasPrevious() {
                return current.parent != null;
            }

            @Override
            public T previous() {
                T t = current.t;
                current = current.parent;
                return t;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {
                Node parent = current.parent;
                parent.next = current.next;
            }

            @Override
            public void set(T t) {
            }

            @Override
            public void add(T t) {
            }
        };
    }


    /**
     * 结点类
     * @param <T>
     */
    class Node<T>{
        T t;
        Node next;
        Node parent;
        public Node() {}
        public Node(T t) {
            this.t = t;
        }
    }


}
