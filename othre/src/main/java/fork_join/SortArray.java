package fork_join;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * @author ypt
 * @ClassName SortArray
 * @Description TODO
 * @date 2019/4/2 15:24
 */
public class SortArray {

    private int[] data;
    private int size;

    public SortArray(){}
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

    public int getSize() {
        return size;
    }

    /**
     * 交换两个数据的值
     * @param data
     * @param l
     * @param r
     */
    public void swap(int l, int r) {
        swap(data,l,r);
    }

    /**
     * 交换两个数据的值
     * @param data
     * @param l
     * @param r
     */
    public void swap(int[] data, int l, int r) {
        int datum = data[l];
        data[l] = data[r];
        data[r] = datum;
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
        return data[i]<data[j];
    }

    public boolean isSorted(){
        for (int i = 1; i < data.length; i++) {
            if (this.less(i,i-1)){
                return false;
            }
        }
        return true;
    }


    public static int[] randomInt(int n){
        try {
            int[] rs = new int[n];
            Random random = new Random();
            for (int i = 0; i < n; i++) {
                rs[i] = random.nextInt(n);
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("random exception!");
        }
    }
}
