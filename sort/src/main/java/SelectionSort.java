import java.util.Arrays;

public class SelectionSort implements SortMethod{


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
        for (int i = 0; i < arr.length; i++) {
            int j = i;
            for (int k = i + 1; k < arr.length; k++) {
                if (arr[k] < arr[j]){
                    j = k;
                }
            }
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
        //System.out.println(Arrays.toString(new SelectionSort().sort(arr)));

        SelectionSort selectionSort = new SelectionSort();
        long sort = selectionSort.testSort(selectionSort, 10000000);
        System.out.println("花费时间"+sort+"ms");
    }
}
