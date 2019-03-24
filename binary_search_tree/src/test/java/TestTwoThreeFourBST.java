import java.util.Random;

public class TestTwoThreeFourBST {

    /**
     * 测试2-3-4树
     * @param args
     */
    public static void main(String[] args) {

        TwoThreeFourBST<Integer> twoThreeFour = new TwoThreeFourBST<Integer>();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            twoThreeFour.insert(new TwoThreeFourBST.DataItem(random.nextInt(100)));
        }


        twoThreeFour.displayTree();


        TwoThreeFourBST.DataItem dataItem = twoThreeFour.find(888);

        System.out.println(dataItem);

        twoThreeFour.printTree();
    }

}
