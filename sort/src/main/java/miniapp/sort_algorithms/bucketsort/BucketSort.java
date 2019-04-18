package miniapp.sort_algorithms.bucketsort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tao
 */
public class BucketSort implements SortMethod {

    @Override
    public String getCnName() {
        return "桶排序";
    }

    /**
     * 桶排序
     *  1.首先找到数组中的最大值，那么此时的桶数组的长度就是最大值加一，
     *      比如最大值为9 那么就是9+1= 10 （1~9）
     *  2.然后将数组的值得个数以值作为索引，个数作为值存入桶数组中
     *  3.最后再把桶数组遍历拿到数组的值，如果ints[index]大于0则为有值 当前arr的值为index，每取一次ints[index]的值就减一
     *      重复这个过程扫描整个桶数组，则arr排序结束
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr) {
        if (arr == null || arr.length <= 1) return arr;
        int max = Integer.MIN_VALUE;
        // 得到最大值
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i],max);
        }
        // 创建max个桶来表示特殊值
        int[] ints = new int[max + 1];
        // 求每个特殊值的个数 并且index处的值为arr数组中的位置
        for (int i = 0; i < arr.length; i++) {
            ints[arr[i]]++;
        }
        // 逐个扫描每个桶 从小到大取出桶中的值放入源数组
        for (int i = 0,j = 0; i < ints.length; i++) {
            while (ints[i] -- > 0){
                arr[j++] = i;
            }
        }
        return arr;
    }

    @Override
    public String methodName() {
        return "BucketSort";
    }

    @Override
    public void destory() {}

    public static void main(String[] args) {
        int[] arr = {2,23,213,22,11,14,5};
        System.out.println(Arrays.toString(new BucketSort().sort(arr)));

        List<Integer> list = new LinkedList<>();
        Collections.sort(list);
        BucketSort bucketSort = new BucketSort();
        long sort = bucketSort.testSort(100000000);
        System.out.println("花费时间"+sort+"ms");
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Pink;
    }

}
