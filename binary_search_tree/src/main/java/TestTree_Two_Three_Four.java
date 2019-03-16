import java.util.Random;

public class TestTree_Two_Three_Four {

    /**
     * 测试2-3-4树
     * @param args
     */
    public static void main(String[] args) {

        Tree_Two_Three_Four<Integer> twoThreeFour = new Tree_Two_Three_Four<Integer>();

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            twoThreeFour.insert(new Tree_Two_Three_Four.DataItem(random.nextInt(10000)));
        }


        //twoThreeFour.displayTree();


        Tree_Two_Three_Four.DataItem dataItem = twoThreeFour.find(888);

        System.out.println(dataItem);
    }


}
