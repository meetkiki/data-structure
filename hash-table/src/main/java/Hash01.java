import java.util.HashMap;
import java.util.Map;

public class Hash01 {


    /**
     * 1.假设我们有 10 万条 URL 访问日志，如何按照访问次数给 URL 排序？
     *  思路：
     *  1.使用散列表存储数据以url作为key，以访问次数为值，同时记录最大访问次数k
     *      如果k不大 使用桶排序 （O（n））
     *      如果k很大（超过10w）使用快速排序  （O(nlogn)）
     */


    /**
     * 2. 有两个字符串数组，每个数组大约有 10 万条字符串，如何快速找出两个数组中相同的字符串？
     *  1.根据第一个字符串数组建立一个散列表，每个字符串的值为key，value为次数
     *  然后循环第二个数组，根据查询散列表key 的值为结果，大于0 为存在，小于0为不存在
     *  时间复杂度为O(n)
     */

    public int majorityElement(int[] nums) {
        int count = 1;
        for(int i = 1;i<nums.length;i++){
            if(nums[0] == nums[i]){
                count++;
            }else{
                count--;
                if(count == 0){
                    nums[0] = nums[i + 1];
                }
            }
        }
        return nums[0];
    }

    public static void main(String[] args) {
        Hash01 hash01 = new Hash01();
        int[] sums = {47,47,72,47,72,47,79,47,12,92,13,47,47,83,33,15,18,47,47,47,47,64,47,65,47,47,47,47,70,47,47,55,47,15,60,47,47,47,47,47,46,30,58,59,47,47,47,47,47,90,64,37,20,47,100,84,47,47,47,47,47,89,47,36,47,60,47,18,47,34,47,47,47,47,47,22,47,54,30,11,47,47,86,47,55,40,49,34,19,67,16,47,36,47,41,19,80,47,47,27};
        System.out.println(hash01.majorityElement(sums));

    }
}
