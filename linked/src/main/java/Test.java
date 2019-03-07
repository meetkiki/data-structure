public class Test {


    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();


        for (int i = 0; i < 100; i++) {
            list.add(i);
        }


        System.out.println(list);



        list.remove(3);


        System.out.println(list);


        list.remove(0);

        System.out.println(list);


        for (int i = 0; i < 100; i++) {
            list.remove(i);
        }
        System.out.println(list);


        LinkedLRUList<Object> lru = new LinkedLRUList<>(2);

        lru.add(1);
        lru.add(2);
        lru.add(3);
        lru.add(2);

        System.out.println(lru);


    }


}
