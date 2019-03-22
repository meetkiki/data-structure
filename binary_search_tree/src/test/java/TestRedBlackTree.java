import org.junit.Test;

public class TestRedBlackTree {

    /**
     * 测试红黑树
     * @throws Exception
     */
    @Test
    public void TestName() throws Exception{
        RedBlackTree<Integer, Object> redBlackTree = new RedBlackTree<>();


        for (int i = 1; i <= 3; i++) {
            redBlackTree.put(i,String.valueOf(i));
        }

        System.out.println(redBlackTree.inorderTraversal());
        System.out.println(redBlackTree.preorderTraversal());

        System.out.println(redBlackTree.get(2));
        System.out.println(redBlackTree.get(20));

        redBlackTree.delete(2);
        System.out.println(redBlackTree.preorderTraversal());



    }

}
