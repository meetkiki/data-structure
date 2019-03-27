import java.util.Random;

public interface SortMethod {


    int[] sort(int[] arr);

    /**
     * 测试排序的效率
     *
     * @param sort
     * @param n
     * @return
     */
    default long testSort(SortMethod sort,int n){
        Random random = new Random();
        int[] ints = new int[n];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt(n);
        }
        long start = System.currentTimeMillis();
        sort.sort(ints);
        return System.currentTimeMillis() - start;
    }


    /**
     * 判断是否有序
     * @param arr
     * @return
     */
    default boolean isSorted(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]){
                return false;
            }
        }
        return true;
    }
}
