import common.Bag;
import org.junit.Test;

public class TestBag {



    @Test
    public void TestName() throws Exception{
        Bag<Integer> bag = new Bag<>();



        bag.addFirst(11);
        bag.addFirst(12);
        bag.addFirst(13);


        bag.removeObj(12);
        bag.removeObj(13);
        bag.removeObj(11);
    }
}
