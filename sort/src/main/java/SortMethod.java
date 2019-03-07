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
}
