package miniapp.abstraction;

import miniapp.Enum.LineColorEnum;

import java.util.Arrays;

/**
 * 排序接口
 * @author Tao
 */
public interface SortMethod extends Sort {


    /**
     * 基本排序算法
     * @param arr
     * @return
     */
    @Override
    int[] sort(int[] arr);

    /**
     * 测试排序的效率
     *
     * @param n
     * @return
     */
    default long testSort(int n){
        int[] ints = this.randomInt(n);
        int[] clone = ints.clone();
        long s1 = System.currentTimeMillis();
        this.sort(ints);
        long e1 = System.currentTimeMillis();
        long s2 = System.currentTimeMillis();
        Arrays.sort(clone);
        long e2 = System.currentTimeMillis();
        boolean sorted = isSorted(ints);
        System.out.println("this sorted is " + sorted);
        System.out.println("Array sorted is " + (e2 - s2) + "ms");
        return e1 - s1;
    }

    /**
     * 排序线颜色
     * @return
     */
    LineColorEnum lineColor();

    /**
     * 排序方法名称
     * @return
     */
    @Override
    String methodName();

    /**
     * 排序方法中文名称
     * @return
     */
    @Override
    String getCnName();

    /**
     * 销毁方法
     */
    void destory();

    /**
     * 排序效率
     * @return
     */
    default String efficiency(){return "fast";}

    /**
     * 比较方法
     * @param arr
     * @param l
     * @param r
     * @return
     */
    default boolean less(int[] arr, int l, int r){
        return arr[l] < arr[r];
    }
}
