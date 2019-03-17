import java.util.Random;

public class TestTreeTwoThreeFour {

    /**
     * 测试2-3-4树
     * @param args
     */
    public static void main(String[] args) {

        TreeTwoThreeFour<Integer> twoThreeFour = new TreeTwoThreeFour<Integer>();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            twoThreeFour.insert(new TreeTwoThreeFour.DataItem(random.nextInt(100)));
        }


        twoThreeFour.displayTree();


        TreeTwoThreeFour.DataItem dataItem = twoThreeFour.find(888);

        System.out.println(dataItem);

        twoThreeFour.printTree();
    }

}
