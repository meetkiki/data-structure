package sort_algorithms;

import abstraction.SortMethod;
import abstraction.SortView;
import view.AlgoFrame;

public class CountingSort implements SortMethod,SortView {
    @Override
    public void sort(AlgoFrame frame) {

    }

    @Override
    public String methodName() {
        return "Counting Sort";
    }

    /**
     * 计数排序
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr) {
        if (arr.length <= 1) return arr;
        int max = arr[0],index = 0;
        // 寻找出最大的值
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max){
                max = arr[i];
            }
        }
        // 最大值加一
        max++;
        // 创建一个临时数组 用于存储每个元素出现的个数
        int[] a = new int[max];
        for (int i = 0; i < arr.length; i++) {
            a[arr[i]]++;
        }
        // 累加前面的值
        for (int i = 1; i < max; i++) {
            a[i] = a[i-1] + a[i];
        }
        // 创建目标数组 保存临时数据
        int[] desc = new int[arr.length];
        for (int i = desc.length - 1; i >= 0; i--) {
            index = a[arr[i]] - 1;
            desc[index] = arr[i];
            a[arr[i]]--;
        }
        // 拷贝临时数据到目标数组
        for (int i = 0; i < desc.length; i++) {
            arr[i] = desc[i];
        }
        return arr;
    }


    public static void main(String[] args) {
        CountingSort countingSort = new CountingSort();
        long sort = countingSort.testSort(countingSort, 1000000);
        System.out.println("花费时间"+sort+"ms");
    }

}
