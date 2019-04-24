import java.util.Iterator;

/**
 * 基于双向链表实现的背包
 * @author Tao
 * @param <T>
 */
public class Bag<T> implements Iterable<T>{

    private Node<T> head = new Node<>();

    /**
     * 插入一个元素
     * @param t
     */
    public void add(T t){
        Node oldNode = head;
        head = new Node<>(t);
        head.next = oldNode;
        oldNode.parent = head;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public T next() {
                T t = current.t;
                current = current.next;
                return t;
            }

            @Override
            public void remove() {
                Node parent = current.parent;
                parent.next = current.next;
            }
        };
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
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
