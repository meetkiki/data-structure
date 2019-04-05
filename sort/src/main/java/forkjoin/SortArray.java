package forkjoin;

import java.util.Random;

/**
 * @author ypt
 * @ClassName SortArray
 * @Description TODO
 * @date 2019/4/2 15:24
 */
public class SortArray {

    private final int[] data;
    private final int size;

    public SortArray(int[] data) {
        this.data = data;
        this.size = data.length;
    }

    /**
     * 重新洗牌
     */
    public void shuffe(){
        Random rnd = new Random();
        // 默认为double
        // Shuffle array
        for (int i=size; i>1; i--)
            swap(data, i-1, rnd.nextInt(i));
    }


    public int get(int i) {
        return data[i];
    }

    public int size() {
        return size;
    }

    /**
     * 交换两个数据的值
     * @param l
     * @param r
     */
    public void swap(int l, int r) {
        swap(data,l,r);
    }

    /**
     * 交换两个数据的值
     * @param arr
     * @param i
     * @param j
     */
    public void swap(int[] arr, int i, int j) {
        if (i != j){
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }

    /**
     * 数据赋值
     * @param data
     */
    public void replace(int l, int val) {
        data[l] = val;
    }

    /**
     * 数据赋值
     * @param data
     */
    public void replace(SortArray data, int l, int val) {
        data.set(l,val);
    }

    /**
     * 设置参数的值
     * @param i
     * @return
     */
    public void set(int i,int val) {
        data[i] = val;
    }


    /**
     * 设置参数的值
     * @param i
     * @return
     */
    public void set(SortArray data,int i,int val) {
        data.getData()[i] = val;
    }

    /**
     * 参数
     * @return
     */
    public int[] getData() {
        return data;
    }

    /**
     * 比较数组中两个索引处值的大小
     * @return
     */
    public boolean less(int i,int j){
        return compare(i,j) < 0;
    }

    /**
     * 比较数组中两个索引处值的大小
     * @return
     */
    public int compare(int i,int j){
        return data[i] - data[j];
    }

    public boolean isSorted(){
        for (int i = 1; i < data.length; i++) {
            if (this.less(i,i-1)){
                return false;
            }
        }
        return true;
    }


    public static void arrayCoppy(SortArray src,int l1,SortArray desc,int l2,int length){
        for (int i = l1; i < l1 + length; i++) {
            desc.set(l2++,src.get(i));
        }
    }


    public static int[] randomInt(int n){
        int[] rs = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            rs[i] = random.nextInt(n);
        }
        return rs;
    }

    public static SortArray insertSort(SortArray data,int l,int r){
        for (int i = l,j = i; i < r; j = ++i) {
            int temp = data.get(i);
            while (temp < data.get(j)){
                // 选取temp放在该放的位置上 这里是temp小于arr[j]时将arr[j]右移
                data.set(j + 1,data.get(j));
                if (j-- == l){
                    break;
                }
            }
            data.set(j + 1,temp);
        }
        return data;
    }
}
