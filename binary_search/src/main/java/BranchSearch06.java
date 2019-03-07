import java.util.Arrays;

public class BranchSearch06 {

    /**
     * 二分查找
     *  查找最后一个小于等于给定值的元素
     *      思路
     *          1.如果mid小于等于给定的元素值。那么这个值一定在mid和r之间
     *          缩小问题后判断mid+1的值是否为最后一个
     * @param arr
     * @param value
     * @return
     */
    public int search(int[] arr,int value){
        if (arr == null || arr.length == 0) return -1;
        int l = 0,r = arr.length - 1;
        while (l <= r){
            int mid = l + ((r - l) >> 1);
            if (arr[mid] <= value){
                if(mid == arr.length - 1 || arr[mid + 1] > value) return mid;
                else l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {2,32,3,5,22,32,21,23,32};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(new BranchSearch06().search(arr,32));
    }

}
