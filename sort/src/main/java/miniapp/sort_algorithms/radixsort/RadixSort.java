package miniapp.sort_algorithms.radixsort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;


/**
 * 基数排序
 * @author Tao
 */
public class RadixSort implements SortMethod {

    private static final int TEN = 10;

    /**
     * 基数排序
     *  基数排序的思想扩展于桶排序 ，将数据分为特殊的关键字收集，然后再进行排序
     *      比如将扑克牌排序可以根据不同的面值分为13份，将其从小到大叠在一起，然后在将每堆按照面值的次序收集到一起
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr) {
        int ex = 1,max = getMax(arr);
        // 从个位开始对数组进行排序
        auxs.set(new int[arr.length]);
        buckets.set(new int[TEN]);
        for (;max/ex > 0; ex*=TEN) {
            // 对每一位进行排序
            countSort(arr,ex);
        }
        return arr;
    }

    /**
     * 临时数组
     */
    private ThreadLocal<int[]> auxs = new ThreadLocal<>();;
    /**
     * 桶
     */
    private ThreadLocal<int[]> buckets = new ThreadLocal<>();;

    /**
     * 获取数组最大值
     * @param arr
     * @return
     */
    private int getMax(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if(max < arr[i]){
                max = arr[i];
            }
        }
        return max;
    }

    /**
     *  对数组按照"某个位数"进行排序(桶排序)
     * @param arr   数据
     * @param ex    对ex位进行排序
     *  例如，对于数组a={50, 3, 542, 745, 2014, 154, 63, 616}；
     *    (01) 当exp=1表示按照"个位"对数组a进行排序
     *    (02) 当exp=10表示按照"十位"对数组a进行排序
     *    (03) 当exp=100表示按照"百位"对数组a进行排序
     */
    private void countSort(int[] arr,int ex){
        int[] bucket = buckets.get();
        int[] aux = auxs.get();
        // 清空
        for (int i = 0; i < buckets.get().length; i++) {
            bucket[i] = 0;
        }
        // 存储每一位的出现次数
        for (int i = 0; i < arr.length; i++) {
            bucket[(arr[i]/ex) % TEN]++;
        }
        // 更改buckets[i]。目的是让更改后的buckets[i]的值，是该数据在output[]中的位置
        // 如果50, 52 , 51, 53 那么 2的桶的值为小于2的所有桶的值 即索引位置 为3-1 = 2
        //     1   3    3   4
        for (int i = 1; i < TEN; i++){
            bucket[i] += bucket[i - 1];
        }
        // 存储到临时数组
        for (int i = arr.length - 1; i >= 0; i--) {
            // 位数的值
            int i1 = (arr[i] / ex) % TEN;
            // 排序后的位置
            aux[--bucket[i1]] = arr[i];
        }
        // 排序好的数组赋值给arr
        for (int i = 0; i < arr.length; i++) {
            arr[i] = aux[i];
        }
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Orange;
    }

    @Override
    public String methodName() {
        return "RadixSort";
    }

    @Override
    public String getCnName() {
        return "基数排序";
    }

    @Override
    public void destory() {
        auxs.remove();
        buckets.remove();
    }


    public static void main(String[] args) {
//        int[] arr = {2,23,213,22,11,14,5};
//        System.out.println(Arrays.toString(new RadixSort().sort(arr)));

        RadixSort radixSort = new RadixSort();
        long sort = radixSort.testSort(10000000);
        System.out.println("花费时间"+sort+"ms");
    }

}
