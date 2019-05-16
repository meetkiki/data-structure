package common;

import lombok.Data;

import java.util.Iterator;

/**
 * 基于双向链表实现的背包
 * @author Tao
 * @param <T>
 */
public class Bag<T> implements Iterable<T>{

    private Node<T> head = new Node<>();
    private int size;

    public Bag() {}
    /**
     * 插入一个元素
     * @param t
     */
    public void addFirst(T t){
        Node oldHead = head;
        head = new Node<>(t);
        head.next = oldHead;
        oldHead.parent = head;
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> current = head;
            Node<T> last = current;

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public T next() {
                last = current;
                current = current.next;
                return last.t;
            }

            @Override
            public void remove() {
                if (last == head){
                    head = head.next;
                    head.parent = null;
                }else{
                    Node parent = last.parent;
                    parent.next = last.next;
                    last.next.parent = parent;
                }
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
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return head.next == null;
    }

    /**
     * 数据长度
     * @return
     */
    public int size(){
        return size;
    }

    /**
     * 批量添加数据
     * @param bag
     */
    public void addAll(Bag<T> bag) {
        if (bag == null || bag.isEmpty()){
            return;
        }
        Iterator<T> it = bag.iterator();
        while (it.hasNext()){
            this.addFirst(it.next());
        }
    }

    /**
     * 删除头
     * @return
     */
    public T deleteFirst(){
        if (head.t == null){
            throw new NullPointerException("头结点为空!");
        }
        size--;
        // 删除头
        Node<T> next = head.next;
        next.parent = null;
        T t = head.t;
        head = next;
        return t;
    }

    /**
     * 获取头
     * @return
     */
    public T first(){
        if (head.t == null){
            throw new NullPointerException("头结点为空!");
        }
        return head.t;
    }

    /**
     * 从头部弹出一个元素
     */
    public T pop() {
        return deleteFirst();
    }

    /**
     * 移除一个元素
     * @param cell
     */
    public void removeObj(T cell) {
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            T next = it.next();
            if (next.equals(cell)){
                it.remove();
                size --;
                break;
            }
        }
    }

    /**
     * 结点类
     * @param <T>
     */
    @Data
    public static class Node<T>{
        T t;
        Node next;
        Node parent;
        public Node() {}
        public Node(T t) {
            this.t = t;
        }
    }


}
