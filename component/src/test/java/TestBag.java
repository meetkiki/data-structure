import common.Bag;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;

public class TestBag {



    @Test
    public void TestName() throws Exception{
        Bag<Integer> bag = new Bag<>();



        bag.addFirst(11);
        bag.addFirst(12);
        bag.addFirst(13);
//
//        long st = System.currentTimeMillis();
//        Bag<Object> bag1 = new Bag<>();
//        for (int i = 0; i < 100000; i++) {
//            bag1.addFirst(i);
//            bag1.indexOf(i / 2);
//            if ((i & 1) == 1) bag1.removeObj(i / 3);
//        }
//        long ed = System.currentTimeMillis();
//        System.out.println("Bag耗时 : "+ (ed - st) + " ms");


        long st2 = System.currentTimeMillis();
        for (int i1 = 0; i1 < 1000000; i1++) {
            LinkedList<Object> bag2 = new LinkedList<>();
            for (int i = 0; i < 100; i++) {
                bag2.addFirst(i);
    //            bag2.contains(i / 2);
    //            if ((i & 1) == 1) bag2.remove((Object) (i / 3));
                if ((i & 1) == 1) bag2.removeLast();
            }
        }
        long ed2 = System.currentTimeMillis();
        System.out.println("LinkedList 耗时 : "+ (ed2 - st2) + " ms");

        long st3 = System.currentTimeMillis();
        for (int i1 = 0; i1 < 1000000; i1++) {
            HashSet<Object> bag3 = new HashSet<>();
            for (int i = 0; i < 100; i++) {
                bag3.add(i);
    //            bag3.contains(i / 2);
    //            if ((i & 1) == 1) bag3.remove((i / 3));
                if ((i & 1) == 1) bag3.remove(i / 2);
            }
        }
        long ed3 = System.currentTimeMillis();
        System.out.println("HashSet 耗时 : "+ (ed3 - st3) + " ms");

        bag.removeObj(12);
        bag.removeObj(13);
        bag.removeObj(11);
    }
}
