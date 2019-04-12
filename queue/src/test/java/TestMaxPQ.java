import org.junit.Test;

import java.util.Random;

public class TestMaxPQ {


    @Test
    public void TestName() throws Exception{
        int[] arr = {323,213,123,121,23,12,222,11,2233};
        MaxPQ<Integer> pq = new MaxPQ<>();

        int n = 2000,min = Integer.MAX_VALUE;
        int[] rs = new int[2000];
        Random random = new Random();
        for (int i = 0; i < 2000; i++) {
            rs[i] = random.nextInt(2000);
            if (rs[i] < min){
                min = rs[i];
            }
        }

        for (int r : rs) {
            pq.insert(r);
        }

        System.out.println(pq);

        System.out.println("min " + min);
        for (int i = 0; i < n - 1; i++) {
            pq.deleteMax();
        }
        System.out.println(pq);
    }

}
