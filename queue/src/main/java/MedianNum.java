import java.util.Arrays;

/**
 * 求中位数
 */
public class MedianNum {

    /**
     * 利用两个堆  大顶堆和小顶堆 大顶堆存储数据前半部分 小顶堆存储后半部分
     *  大顶堆存储0 - n/2 小顶堆存n/2 - n
     *  如果n是奇数，那么大顶堆存n/2+1个 小顶堆存储n/2+1 - n
     */
    MinPQ<Integer> minpq = new MinPQ();
    MaxPQ<Integer> maxpq = new MaxPQ();

    /**
     * 新增元素
     * @param i
     * @return
     */
    public Integer insert(int i){
        // 如果插入的数据大于大顶堆的最大值 那么就入最小堆 否则插入最大堆
        // 大顶堆存储小于中位数的值 小顶堆存储大于中位数的值
        maxpq.insert(i);
        minpq.insert(maxpq.deleteMax());
        if (maxpq.size() < minpq.size()){
            maxpq.insert(minpq.poll());
        }
        return maxpq.max();
    }

    public Integer findMedianNum(){
        if (maxpq.size() == minpq.size()){
            return ((maxpq.max() + minpq.min()) >> 1);
        }else{
            return maxpq.max();
        }
    }


    public static void main(String[] args) {
        MedianNum medianNum = new MedianNum();
        int[] arr = {323,213,123,121,23,12,222,11,2233};

        for (int i = 0; i < arr.length; i++) {
            medianNum.insert(arr[i]);
        }
        Arrays.sort(arr);
        System.out.println(arr[arr.length / 2]);
        System.out.println(medianNum.findMedianNum());
    }

}
