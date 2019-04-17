/**
 * 小顶堆
 * @author Tao
 * @param <T>
 */
public class MinPQ<T extends Comparable<T>> extends MaxPQ<T> {

    /**
     * 小顶堆则是在堆顶为最小元素
     * @param i
     * @param j
     * @return
     */
    @Override
    protected boolean less(int i, int j) {
        return data[i].compareTo(data[j]) > 0;
    }


    public T min(){
        return data[1];
    }

    /**
     * 弹出元素
     * @return
     */
    public T poll(){
        return deleteMax();
    }

    public static void main(String[] args) {
        MinPQ<Integer> minPQ = new MinPQ<>();
        int[] arr = {323,213,123,121,23,12,222,11,2233};
        for (int i = 0; i < arr.length; i++) {
            minPQ.insert(arr[i]);
        }

        System.out.println(minPQ);
    }

}
