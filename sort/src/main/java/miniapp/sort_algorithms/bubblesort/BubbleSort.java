package miniapp.sort_algorithms.bubblesort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.awt.Color;
import java.util.Arrays;

public class BubbleSort implements SortMethod {


    @Override
    public String getCnName() {
        return "冒泡排序";
    }

    /**
     * 冒泡排序
     */
    @Override
    public int[] sort(int[] arr){
        int temp;
        for (int i = 0; i < arr.length; i++) {
            boolean flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j+1]){
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                    flag = true;
                }
            }
            // 没有数据交换，提前推出
            if (!flag) break;
        }
        return arr;
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.valueOf("Red");
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,22,2};
        System.out.println(Arrays.toString(new BubbleSort().sort(arr)));
    }

}
