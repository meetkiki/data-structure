import java.util.Arrays;

public class BranchSearch04 {


    /**
     * 二分查找
     *
     *  查找最后一个等于该值的值
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
            if (arr[mid] > value){
                r = mid - 1;
            }else if(arr[mid] < value){
                l = mid + 1;
            }else{
                // 等于情况
                if (mid == arr.length - 1 || arr[mid + 1] != value )
                    return mid;
                else{
                    l = mid + 1;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] arr = {2,32,3,5,22,32,21,23,32};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(new BranchSearch04().search(arr,32));
    }

}
