import java.util.Arrays;

public class BranchSearch03 {

    /**
     * 二分查找
     *  查找第一个等于value的索引
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
                if (l != 0 && arr[l - 1] == value){
                    r = mid - 1;
                }else{
                    return mid;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] arr = {2,32,3,5,22,32,21,23,32};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(new BranchSearch03().search(arr,32));
    }

}
