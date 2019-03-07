import java.util.Arrays;

public class BranchSearch02 {

    /**
     *  二分查找
     *      递归实现
     * @param arr
     * @param value
     * @return
     */
    public int search(int[] arr,int value){
        return bsearchInternally(arr,0,arr.length - 1,value);
    }

    private int bsearchInternally(int[] arr, int low, int high, int value) {
        if (low > high) return -1;
        int mid = low + ((high - low) >> 1);
        if (arr[mid] == value){
            return mid;
        } else if (arr[mid] > value) {
            return bsearchInternally(arr,low,mid - 1,value);
        } else {
            return bsearchInternally(arr,mid+1,high,value);
        }
    }


    public static void main(String[] args) {
        int[] arr = {2,23,22,10,20,123};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(new BranchSearch02().search(arr,123));
    }

}
