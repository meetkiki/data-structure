import java.util.Arrays;

public class BranchSearch05 {

    /**
     * 二分查找
     *  查找第一个大于等于给定值的元素
     *  思路：
     *      1.如果arr[mid] 大于等于 value,那么第一个就在l和mid之间
     *      进而缩小问题
     *
     * @param arr
     * @param value
     * @return
     */
    public int search(int[] arr,int value){
        if (arr == null || arr.length == 0) return -1;
        int l = 0,r = arr.length - 1;
        while (l <= r){
            int mid = l + ((r - l) >> 1);
            if (arr[mid] >= value){
                if(mid == 0 || arr[mid - 1] != value) return mid;
                r = mid - 1;
            }else{
                l = mid + 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] arr = {2,32,3,5,22,32,21,23,32};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(new BranchSearch05().search(arr,32));
    }

}
