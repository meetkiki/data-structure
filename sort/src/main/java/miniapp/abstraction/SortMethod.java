package miniapp.abstraction;

import java.util.Arrays;

/**
 * 排序接口
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
        System.out.println("sorted is " + isSorted(ints));
        System.out.println("this Sort is " + (e1 - s1) + "ms");
        System.out.println("Arrays sort is " + (e2 - s2) + "ms");
        return e1;
    }


}
