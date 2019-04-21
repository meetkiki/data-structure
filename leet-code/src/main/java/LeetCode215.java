
public class LeetCode215 {

    /**
     * 数组中的第K个最大元素
     *
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     *
     * 示例 1:
     *
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     * 示例 2:
     *
     * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
     * 输出: 4
     * 说明:
     *
     * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
     */
    public int findKthLargest(int[] nums, int k) {
        int l = 0,r = nums.length - 1;
        k = r - k + 1;
        while (r > l){
            // pivot就是第pivot小的元素
            int pivot = partition(nums,l,r);
            if (pivot == k) break;
            else if (pivot > k) r = pivot - 1;
            else l = pivot + 1;
        }
        return nums[k];
    }

    /**
     * 快排切分
     * @param nums
     * @param l
     * @param r
     * @return
     */
    private int partition(int[] nums, int l, int r) {
        int v = nums[l],lt = l,gt = r + 1;
        while (true){
            // 找到第一个比v大的元素
            while (nums[++lt] < v){
                if (lt == r){
                    break;
                }
            }
            // 找到第一个比v小的元素
            while (nums[--gt] > v){
                if (gt == l){
                    break;
                }
            }
            // 如果已经找到直接退出
            if (lt >= gt){
                break;
            }
            // 交换lt和gt
            swap(nums,lt,gt);
        }
        // lt和gt交换后 v就是gt
        swap(nums,l,gt);
        return gt;
    }


    private void swap(int[] nums, int l, int r){
        int temp = nums[l];
        nums[l] = nums[r];
        nums[r] = temp;
    }

}
