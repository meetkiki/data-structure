/**
 * @author ypt
 * @ClassName TestBinarySearchTree
 * @Description 测试查询二叉树
 * @date 2019/3/10 19:05
 */
public class TestBinarySearchTree {

    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        int[] arr = {5,2,10,22,3};
        for (int i = 0; i < arr.length; i++) {
            binarySearchTree.insert(arr[i]);
        }

        /**
         *          5
         *        /  \
         *       2    10
         *       \     \
         *        3     22
         */
        // 前序遍历 5 2 3 10 22 先打印本身,在打印左节点,最后打印右节点
        System.out.println(binarySearchTree.preorderPrint());
        // 中序遍历 2 3 5 10 22 先打印左节点,再打印本身,最后打印右节点
        System.out.println(binarySearchTree.inorderPrint());
        // 后序遍历 3 2 22 10 5先打印左节点,再打印右节点,最后打印本身
        System.out.println(binarySearchTree.postorderPrint());

        // 查找  Node{data=3}
        System.out.println(binarySearchTree.find(3));
        // 删除节点
        binarySearchTree.delete(2);
        // 前序遍历 [5, 3, 10, 22]
        System.out.println(binarySearchTree.preorderPrint());
        // null
        System.out.println(binarySearchTree.find(2));
        // 高度 3
        System.out.println(binarySearchTree.getHeight());
        // 宽度 2
        System.out.println(binarySearchTree.getWeight());
        // 反转二叉树
        //binarySearchTree.reversalTree();
        // 中序遍历 [10, 22, 5, 3]
        System.out.println(binarySearchTree.inorderPrint());

        // 向下取整
        System.out.println(binarySearchTree.floor(6));
        // 最大最小键
        System.out.println(binarySearchTree.min());
        System.out.println(binarySearchTree.max());
    }

}
