
public class Test {

    public static void main(String[] args) {
        Array<Integer> array = new Array<>();

        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);


        System.out.println(array);
        System.out.println("array size "+ array.size());


        System.out.println(array.find(5));
    }
}
