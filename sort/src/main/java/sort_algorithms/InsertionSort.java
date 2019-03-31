package sort_algorithms;

import abstraction.SortMethod;
import abstraction.SortView;
import view.AlgoFrame;

public class InsertionSort implements SortMethod, SortView {

    /**
     * 插入排序
     *  我们将数组中的数据分为两个区间，已排序区间和未排序区间。
     *
     *  初始已排序区间只有一个元素，就是数组的第一个元素
     *  插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入
     *  并保证已排序区间数据一直有序。重复这个过程，直到未排序区间中元素为空，算法结束
     */
    @Override
    public int[] sort(int[] arr){
        int temp;
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            temp = arr[i];
            for (; j >= 0; j--) {
                // 选取temp放在该放的位置上 这里是temp小于arr[j]时将arr[j]右移
                if (temp < arr[j]){
                    arr[j + 1] = arr[j];
                }else{
                    break;
                }
            }
            arr[j + 1] = temp;
        }
        return arr;
    }

    /**
     * 可视化插入排序
     *
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        int n = frame.length();
        // 初始化数组
        frame.setData(0,-1,-1);
        // 假定从1开始前面都是有序的 从无序的每个元素中依次插入到有序的数组中达到完全有序
        for (int i = 1; i < n; i++) {
            // 判断数据当前值是否小于已排序好值，找到当前数据合适的位置
            for (int j = i; j > 0 && frame.less(j,j - 1); j--) {
                frame.setData(i + 1, j,j - 1);
                frame.swap(j,j - 1);
            }
        }
        // 格式化数据
        frame.setData(n,-1,-1);
    }

    @Override
    public String methodName() {
        return "Insert sort";
    }

    public static void main(String[] args) {
        InsertionSort insertionSort = new InsertionSort();
        long sort = insertionSort.testSort(insertionSort, 1000000);
        System.out.println("花费时间"+sort+"ms");
    }
}
