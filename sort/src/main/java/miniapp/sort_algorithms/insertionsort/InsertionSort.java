package miniapp.sort_algorithms.insertionsort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

public class InsertionSort implements SortMethod {

    @Override
    public void destory() {}

    @Override
    public String getCnName() {
        return "插入排序";
    }


    @Override
    public String methodName() {
        return "InsertionSort";
    }
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

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.valueOf("Indigo");
    }


    public static void main(String[] args) {
        InsertionSort insertionSort = new InsertionSort();
        long sort = insertionSort.testSort(1000000);
        System.out.println("花费时间"+sort+"ms");
    }

}
