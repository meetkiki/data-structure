import org.junit.Test;

import java.util.Random;

public class TestIndexMinPQ {

    @Test
    public void TestName() throws Exception{

        int n = 2000,min = Integer.MAX_VALUE;
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(n);
        int[] arr = {323,213,123,121,23,12,222,11,2233};
        for (int i = 0; i < arr.length; i++) {
            pq.insert(i,arr[i]);
        }

        System.out.println(pq);

        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int i1 = random.nextInt(n);
            pq.insert(i,i1);
            if (i1 < min){
                min = i1;
            }
        }

        System.out.println(pq);


    }
}
