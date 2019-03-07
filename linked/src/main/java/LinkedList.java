/**
 * 单向链表实现list
 * @param <T>
 */
public class LinkedList<T> implements List<T> {

    //头结点
    protected Node head;

    protected class Node<T>{
        T data;
        Node next;

        Node(){}
        Node(T t){
            this.data = t;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    @Override
    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        if (head == null){
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null){
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean remove(T t) {
        if (head == null){
            return false;
        }
        if (head.getData().equals(t)){
            head = head.next;
            return true;
        }
        Node temp = head.next,pre = head;
        while (temp != null){
            if (temp.getData().equals(t)){
                pre.next = temp.next;
                return true;
            }
            pre = temp;
            temp = temp.next;
        }
        return false;
    }

    @Override
    public int size() {
        int size = 0;
        if (head == null){
            return size ++;
        }
        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
            size ++;
        }
        return size;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        Node temp = head;
        buffer.append("[");
        while (temp != null){
            buffer.append(temp.getData());
            if (temp.next != null){
                buffer.append(",");
            }
            temp = temp.next;
        }
        buffer.append("]");
        return buffer.toString();
    }



}
