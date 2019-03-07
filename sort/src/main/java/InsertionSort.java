import java.util.Arrays;

public class InsertionSort {


    /**
     * 选择排序
     *  我们将数组中的数据分为两个区间，已排序区间和未排序区间。
     *
     *  初始已排序区间只有一个元素，就是数组的第一个元素
     *  插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入
     *  并保证已排序区间数据一直有序。重复这个过程，直到未排序区间中元素为空，算法结束
     */
    public int[] sort(int[] arr){
        int temp;
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            temp = arr[i];
            for (; j >= 0; j--) {
                // 每回合从后面数据中寻找最小的索引
                if (temp > arr[j]){
                    arr[j + 1] = arr[j];
                }else{
                    break;
                }
            }
            arr[j + 1] = temp;
        }
        return arr;
    }


    public static void main(String[] args) {
        int[] arr = {22,2,3,42,4,23,12};
        System.out.println(Arrays.toString(new InsertionSort().sort(arr)));
    }
}
