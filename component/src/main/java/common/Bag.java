package common;

import lombok.Data;

import java.util.Comparator;
import java.util.Iterator;

/**
 * 基于双向链表实现的背包
 * @author Tao
 * @param <T>
 */
public class Bag<T> implements Iterable<T> {

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
     * 查询元素是否存在 返回索引
     * @param cell
     */
    public int indexOf(T cell) {
        int index = -1;
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            index++;
            T next = it.next();
            if (next.equals(cell)){
                return index;
            }
        }
        return -1;
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


    /**
     * 排序方法 小数据这里先暂时使用插入排序
     * @param bag
     * @return
     */
    public Bag<T> sort(Bag<T> bag,Comparator<T> com){
        if (bag.size == 0){
            return bag;
        }
        // 有序结点
        Node<T> next = bag.head;
        // 待排序结点
        Node<T> curr = bag.head.next;
        // 辅助结点
        Node<T> aux = new Node<>();
        aux.next = bag.head;
        while (curr.next != null) {
            // 如果待排序值小于当前排序好的值 则插入到头部
            if (com.compare(curr.t,next.t) < 0){
                //先把curr节点从当前链表中删除，然后再把cur节点插入到合适位置
                next.next = curr.next;
                //从前往后找到l2.val>cur.val,然后把cur节点插入到l1和l2之间
                Node<T> l1 = aux;
                Node<T> l2 = aux.next;
                while (com.compare(curr.t,l2.t) > 0){
                    l1 = l2;
                    l2 = l2.next;
                }
                //把cur节点插入到l1和l2之间
                l1.next = curr;
                //插入合适位置
                curr.next = l2;
                //指向下一个待处理节点
                curr = next.next;
            }else{
                next = curr;
                curr = curr.next;
            }
        }
        bag.head = aux.next;
        return bag;
    }




}
