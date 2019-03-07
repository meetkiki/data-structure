import java.util.Arrays;

public class MergeSort implements SortMethod{


    /**
     * 归并排序
     *  ① 分解 -- 将当前区间一分为二，即求分裂点 mid = (low + high)/2;
     *  ② 求解 -- 递归地对两个子区间a[low...mid] 和 a[mid+1...high]进行归并排序。递归的终结条件是子区间长度为1。
     *  ③ 合并 -- 将已排序的两个子区间a[low...mid]和 a[mid+1...high]归并为一个有序的区间a[low...high]。
     *      合并的思路
     *          1.创建一个临时数组，从两个子数组的初始索引开始遍历数组放入临时数组
     *          2.判断哪个子数组仍然有数据，放入临时数组
     *          3.将临时数组的数据复制回原数组
     * @param arr
     * @return
     */
    @Override
    public int[] sort(int[] arr){
        int length = arr.length,l = 0,r = length - 1;
        mergesort(arr,l,r);
        return arr;
    }

    private void mergesort(int[] arr, int l, int r) {
        // 递归终止条件
        if (l >= r) return;
        // 取 l到r中间的位置
        int q = (l+r)/2;
        // 分治递归
        mergesort(arr,l,q);
        mergesort(arr,q+1,r);
        // 将arr合并
        merge(arr,l,q,r);
    }

    private void merge(int[] arr, int l,int q, int r) {
        int i = l,j = q + 1,k = 0,tsize = r - l + 1;
        int[] temp = new int[tsize];
        while (i <= q && j <= r){
            if (arr[i]<arr[j]){
                temp[k++] = arr[i++];
            }else{
                temp[k++] = arr[j++];
            }
        }
        // 判断哪个子数组中有数据
        int start = i,end = q;
        if (j <= r){
            start = j;
            end = r;
        }
        // 将剩余的数据拷贝到临时数组
        while (start <= end){
            temp[k++] = arr[start++];
        }
        // 将temp数组的数据拷贝回arr
        for (int m = 0; m < tsize; m++) {
            arr[l+m] = temp[m];
        }
    }


    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        long sort = mergeSort.testSort(mergeSort, 10000000);
        System.out.println("花费时间"+sort+"ms");
        //花费时间1712ms 花费时间1841ms 花费时间1877ms

        //System.out.println(Arrays.toString(new BubbleSort().sort(ints)));
    }


}
