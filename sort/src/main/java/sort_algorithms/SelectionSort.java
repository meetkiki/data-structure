package sort_algorithms;

import view.AlgoFrame;

public class SelectionSort implements SortMethod{

    @Override
    public void sort(AlgoFrame frame) {
        // 选择排序初始化
        frame.setData(0, -1, -1);
        for(int i = 0; i < frame.length() ; i ++ ){
            // 寻找[i, n)区间里的最小值的索引
            int minIndex = i;
            frame.setData(i, -1, minIndex);
            for(int j = i + 1; j < frame.length() ; j ++ ){
                frame.setData(i, j, minIndex);
                if(frame.less(j,minIndex)){
                    minIndex = j;
                    frame.setData(i, j, minIndex);
                }
            }
            frame.swap(i , minIndex);
            frame.setData(i + 1, -1, -1);
        }
        frame.setData(frame.length(),-1,-1);
    }

    @Override
    public String methodName() {
        return "Selection Sort";
    }

    /**
     * 选择排序
     *  选择排序算法的实现思路有点类似插入排序，也分已排序区间和未排序区间。
     *
     *  但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
     * @param arr
     * @return
     */
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


    public static void main(String[] args) {
        //int[] arr = {1,2,32,3,22,11,21};
        //System.out.println(Arrays.toString(new sort_algorithms.SelectionSort().sort(arr)));

        SelectionSort selectionSort = new SelectionSort();
        long sort = selectionSort.testSort(selectionSort, 1000000);
        System.out.println("花费时间"+sort+"ms");
    }
}
