import org.junit.Test;

public class TestMaxPQ {


    @Test
    public void TestName() throws Exception{
        int[] arr = {323,213,123,121,23,12,222,11,2233};
        MaxPQ<Integer> pq = new MaxPQ<>();

        for (int i : arr) {
            pq.insert(i);
        }

        System.out.println(pq);

        pq.deleteMax();

        System.out.println(pq);

        pq.deleteMax();

        System.out.println(pq);
    }

}
