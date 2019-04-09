package miniapp.sort_algorithms.selectionsort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

public class SelectionSort implements SortMethod {
    @Override
    public String methodName() {
        return "SelectionSort";
    }

    @Override
    public String getCnName() {
        return "选择排序";
    }

    /**
     * 选择排序
     *  选择排序算法的实现思路有点类似插入排序，也分已排序区间和未排序区间。
     *
     *  但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr){
        int temp;
        // 从头扫描 即最小值逐渐递增
        for (int i = 0; i < arr.length; i++) {
            int j = i;
            // 获取剩余数据的最小值
            for (int k = i + 1; k < arr.length; k++) {
                if (arr[k] < arr[j]){
                    j = k;
                }
            }
            // 将最小值放在头部
            if (j != i){
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        return arr;
    }
    @Override
    public void destory() {}

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.LightSlateGray;
    }

    public static void main(String[] args) {
        //int[] arr = {1,2,32,3,22,11,21};
        //System.out.println(Arrays.toString(new miniapp.sort_algorithms.selectionsort.SelectionSort().sort(arr)));

        SelectionSort selectionSort = new SelectionSort();
        long sort = selectionSort.testSort(1000000);
        System.out.println("花费时间"+sort+"ms");
    }
}
