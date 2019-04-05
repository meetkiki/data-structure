package forkjoin;

import miniapp.abstraction.Sort;

import java.util.Random;

/**
 * @author ypt
 * @ClassName SortArray
 * @Description TODO
 * @date 2019/4/2 15:24
 */
public class SortArray implements Sort {

    private final int[] data;
    private final int size;

    public SortArray(int size) {
        this.data = randomInt(size);
        this.size = size;
    }

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
        return this.isSorted(data);
    }


    public static void arrayCoppy(SortArray src,int l1,SortArray desc,int l2,int length){
        for (int i = l1; i < l1 + length; i++) {
            desc.set(l2++,src.get(i));
        }
    }

    public SortArray insertSort(SortArray data, int l, int r){
        this.insertSort(data.getData(),l,r);
        return data;
    }

    public int[] cloneData(){
        return data.clone();
    }

    public SortArray insertSort(int l, int r) {
        this.insertSort(data,l,r);
        return this;
    }
}
