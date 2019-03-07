/**
 * 基于数组实现list
 * @param <T>
 */
public class Array<T> implements List<T>{
    /**
     * 数据
     */
    private T[] data;
    /**
     * 数据的元素个数
     */
    private int size;
    /**
     * 数组的容量
     */
    private int capacity;

    /**
     * 无参构造
     */
    public Array(){
        this(10);
    }

    /**
     * 有参构造
     * @param capacity
     */
    public Array(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * 添加方法
     * @param t
     * @return
     */
    @Override
    public boolean add(T t) {
        if (size >= capacity){
            //扩容拷贝
            resize(2 * capacity);
        }
        data[size] = t;
        size++;
        return true;
    }

    /**
     * 数组扩容拷贝
     * @param resize
     */
    private void resize(int resize) {
        T[] temp = (T[]) new Object[resize];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i];
        }
        capacity = resize;
        data = temp;
    }

    /**
     * 获取方法
     * @param index
     * @return
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("下标越界！");
        }
        return data[index];
    }

    /**
     * 查找第一个方法
     * @param t
     * @return
     */
    @Override
    public int find(T t) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(t)){
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回是否為空
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 刪除元素
     * @param t
     * @return
     */
    @Override
    public boolean remove(T t) {
        int index = find(t);
        return remove(index);
    }

    /**
     * 根据索引删除某一个元素
     * @param index
     * @return
     */
    public boolean remove(int index) {
        if (index < 0 || index >= size){
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            data[index] = data[index + 1];
        }
        if (size == capacity / 4 && capacity / 2 != 0){
            resize(capacity / 2);
        }
        return true;
    }

    /**
     * 返回数组元素大小
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 打印方法
     * @return
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 0; i < size ; i++) {
            buffer.append(data[i]);
            if (i != size - 1){
                buffer.append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
