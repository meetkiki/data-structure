import org.junit.Test;

import java.util.LinkedList;

public class MyTest {


    @Test
    public void TestList() throws Exception{

        LinkedList<Object> list = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }


        System.out.println(list);


//        Iterator<Object> it = list.iterator();
//
//        while (it.hasNext()){
//            Object next = it.next();
//
//
//            list.addFirst(1);
//            list.removeFirst();
//
//            System.out.println(next);
//        }


//        for (int i = 0; i < list.size(); i++) {
//            list.addFirst(1);
//            list.removeFirst();
//
//            System.out.println(list.get(i));
//        }

        for (Object o : list) {
            list.addFirst(1);
            list.removeFirst();
            System.out.println(o);
        }

    }

}
