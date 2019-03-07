import java.util.Arrays;

public class BranchSearch01 {

    /**
     * 二分查询
     *      循环实现
     * @param arr
     * @param value
     * @return
     */
    public int search(int[] arr ,int value){
        if (arr == null || arr.length  == 0) return -1;
        int l = 0,r = arr.length - 1;
        while (l<=r){
            int mid = l + ((r - l) >> 1);
            if (arr[mid] == value){
                return mid;
            }else if(arr[mid] < value){
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] arr = {2,23,22,10,20,123};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(new BranchSearch01().search(arr,123));
    }

}
