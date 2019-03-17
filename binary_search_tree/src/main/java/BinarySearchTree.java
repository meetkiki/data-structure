import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author ypt
 * @ClassName Node
 * @Description 节点类
 * @date 2019/3/10 15:45
 */
public class BinarySearchTree {

    private Node tree;

    /**
     * 插入方法
     *  1.判断根节点是否为空 如果为空则直接赋值
     *  2.判断是否大于当前节点 如果大于则判断右子节点,否则判断左子节点
     *  3.如果子节点为null则直接插入 否则继续遍历
     * @param t
     */
    public void insert(int t){
        if (null == tree){
            tree = new Node(t);
            return;
        }
        Node p = tree;
        while (p != null){
            // 如果当前值大于data则放在右子树
            if (t > p.data){
                if (p.right == null){
                    p.right = new Node(t);
                    return;
                }
                p = p.right;
            }else{
                // t < p.data
                if (p.left == null){
                    p.left = new Node(t);
                    return;
                }
                p = p.left;
            }
        }
    }

    /**
     * 查询方法
     *  1.如果当前值等于p节点的值直接返回
     *  2.如果当前值大于p节点的值则向右子节点查找
     *  3.如果当前值小于p节点的值则向左子节点查找
     * @param t
     * @return
     */
    public Node find(int t){
        Node p = tree;
        while (p != null){
            if (p.data == t){
                return p;
            }else{
                if (p.data < t){
                    p = p.right;
                }else{
                    p = p.left;
                }
            }
        }
        return null;
    }

    /**
     * 删除方法
     *  1.删除主要是判断当前节点是否含有子节点
     *  2.如果删除的节点不含有子节点 直接将该节点的父节点赋为null即可
     *  2.删除的节点含有两个子节点,需要找到右子节点中最小的数据放到删除的位置
     *  4.如果删除的节点只包含一个子节点或者删除的是叶子结点
     * @param t
     */
    public void delete(int t){
        // 删除节点和该节点的父节点
        Node p = tree,pp = null;
        while (p != null && p.data != t){
            // 存储父节点的地址
            pp = p;
            if (p.data < t){
                p = p.right;
            } else {
                p = p.left;
            }
        }
        // 未找到
        if (p == null) return;
        // 1.删除的节点含有两个子节点,需要找到右子节点中最小的数据放到删除的位置
        if (p.right != null && p.left != null){
            // 寻找右节点中最小节点 t1为左节点 t2为父节点
            Node t1 = p.right,t2 = p;
            while (p.left != null){
                t2 = t1;
                t1 = t1.left;
            }
            // 替换左节点和删除节点的值
            p.data = t1.data;
            // 删除pp和p p为左节点 pp为父节点
            pp = t2;
            p = t1;
        }
        // 2.如果删除的节点只包含一个子节点或者删除的是叶子结点
        // p的子节点
        Node child;
        if (p.left != null){
            child = p.left;
        }else if(p.right != null){
            child = p.right;
        }else{
            child = null;
        }
        // 如果删除是根节点
        if (pp == null){
            tree = child;
        }else if (pp.left == p){
            pp.left = child;
        }else{
            pp.right = child;
        }
    }

    /**
     * 查找最小键
     *  1.向左查找，如果当前节点的左节点为空则为最小节点 ，最大节点于此同理
     * @return
     */
    public Node min(){
        return min(tree);
    }

    private Node min(Node node){
        if (node.left == null) return node;
        return min(node.left);
    }

    /**
     * 查找最大键
     *  1.向右查找，如果当前节点的右节点为空则为最大节点 ，最小节点于此同理
     * @return
     */
    public Node max(){
        return max(tree);
    }

    private Node max(Node node){
        if (node.right == null) return node;
        return max(node.right);
    }

    /**
     * 向下取整
     *  即取不大于x的最大整数
     *  （与“四舍五入”不同，下取整是直接取按照数轴上最接近要求值的左边值，即不大于要求值的最大的那个值）
     *  (向上取整于此同理)
     * @param key
     * @return
     */
    public Node floor(int key){
        return floor(tree,key);
    }

    /**
     * 思路
     *  1.如果给定的键key小于二叉查找树的根节点的键，那么小于等于key的最大键floor（key一定在根节点的左子树中）
     *  2.如果给定的键key大于二叉查找树的根节点的键，那么只有当根节点右子树中存在小于等于key的结点时，小于等于key的最大键才存在于右子树中
     *      否则根节点就是小于等于key的最大键。
     * @param tree
     * @param key
     * @return
     */
    private Node floor(Node tree, int key) {
        if (tree == null) return null;
        if (tree.data == key) return tree;
        if (tree.data > key) return floor(tree.left,key);
        Node floor = floor(tree.right, key);
        if (floor != null) return floor;
        return tree;
    }

    /**
     * 假设我们需要查找排名为k的结点，就需要实现select方法
     *  1.如果左子树的结点数t大于k，那么我们就继续(递归地)在左子树中查找排名为k的键；如果t=k，那么我们就返回根节点中的键
     *  2.如果t小于了k，我们就递归的从右子树中查找排名为k-t-1的键。
     * @param key
     * @return
     */
    public Node select(int key){
        return select(tree,key);
    }

    private Node select(Node tree, int k) {
        if (tree == null) return null;
        if (k == size(tree)){
            return tree;
        }else{
            int size = size(tree);
            if (k > size(tree)){
                return select(tree.right,k - size - 1);
            }else {
                return select(tree.left,k);
            }
        }
    }

    /**
     * 返回当前节点的个数
     * @return
     */
    public int size(){
        return size(tree);
    }

    private int size(Node tree) {
        if (tree == null) return 0;
        //return tree.N;
        return 0;
    }

    /**
     * 翻转二叉树
     *  1.判断当前根节点是否为空，为空不做操作
     *  2.根节点不为空交换左右子节点
     * @param node
     */
    private void reversal(Node node){
        if (node != null){
            // 交换左右子树
            Node temp = node.left;
            node.left = node.right;
            node.right = temp;
            // 继续交换左子树
            reversal(node.left);
            // 继续交换右子树
            reversal(node.right);
        }
    }

    public void reversalTree(){
        reversal(tree);
    }

    /**
     * 前序遍历
     *  先遍历当前节点,再遍历左节点,最后遍历右节点
     * @return
     */
    private void preorderTraversal(Node node,List<Integer> list){
        if (node == null) return ;
        list.add(node.data);
        if (node.left != null) preorderTraversal(node.left,list);
        if (node.right != null) preorderTraversal(node.right,list);
    }


    /**
     * 中序遍历
     *  排序，输出有序的数据
     *  先遍历左节点,再遍历当前节点,最后遍历右节点
     * @return
     */
    private void inorderTraversal(Node node, List<Integer> list){
        if (node == null) return;
        if (node.left != null) inorderTraversal(node.left,list);
        list.add(node.data);
        if (node.right != null) inorderTraversal(node.right,list);
    }


    /**
     * 后序遍历
     *  先遍历右节点,再遍历左节点,最后遍历当前节点
     * @return
     */
    private void postorderTraversal(Node node,List<Integer> list){
        if (node == null) return;
        if (node.left != null) postorderTraversal(node.left,list);
        if (node.right != null) postorderTraversal(node.right,list);
        list.add(node.data);
    }

    /**
     * 打印哈二叉搜索树
     */
    public void printTree() {
        MyWindow jf = new MyWindow();
        jf.setSize(800, 800);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        drawTree(tree, jf, 395, 50, 1);
    }

    /**
     * 画二叉搜索树
     */
    public void drawTree(Node a, MyWindow jf, int x, int y, int level) {
        Graphics g = jf.getGraphics();
        jf.getShapes().add(new Word("第"+level+"层",15,y));
        level++;
        if (a != null){
            jf.getShapes().add(new Area(x - 10,y - 15,30,20));
            jf.getShapes().add(new Word(String.valueOf(a.data), x, y));
            System.out.println("画完节点" + a.data);
        }
        if (a.left != null) {
            int xl = 10,yl = -10;
            jf.getShapes().add(new Line(x - xl, y, x - 150/level + xl, y + 10*level + yl));
            drawTree(a.left, jf, x - 150/level - xl, y + 10*level - yl, level);
        }
        if (a.right != null) {
            int xl = 20,yl = -10;
            jf.getShapes().add(new Line(x + xl, y, x + 150/level + xl, y + 10*level + yl));
            drawTree(a.right, jf, x + 150/level + xl, y + 10*level - yl, level);
        }
    }


    /**
     * 二叉树的深度
     *  1.只有一个根节点时，二叉树深度为1
     *  2.只有左子节点时，二叉树深度为左子节点深度加1
     *  3.只有右子节点时，二叉树深度为右子节点深度加1
     *  4.同时存在左右子节点时，二叉树深度为左右子节点深度最大者加1
     * @param node
     * @return
     */
    private int height(Node node){
        if (node == null) return 0;
        int left = height(node.left);
        int right = height(node.right);
        return Math.max(left,right) + 1;
    }

    /**
     * 二叉树的宽度
     *  1.使用栈完成层序遍历，依次弹出比较左右子树的宽度
     * @param node
     * @return
     */
    private int weith(Node node){
        if (node == null) return 0;
        Stack<Node> stack = new Stack();
        stack.push(node);
        int max = 1,deep = 1;
        while (!stack.isEmpty()){
            while (deep-- > 0){
                Node temp = stack.pop();
                if (temp.left != null){
                    stack.push(temp.left);
                }
                if (temp.right != null){
                    stack.push(temp.right);
                }
            }
            deep = stack.size();
            max = Math.max(max,deep);
        }
        return max;
    }

    public int getWeight(){
        Node temp = tree;
        return weith(temp);
    }

    public int getHeight(){
        Node temp = tree;
        return height(temp);
    }

    public List<Integer> preorderPrint(){
        List<Integer> list = new ArrayList();
        Node temp = tree;
        preorderTraversal(temp,list);
        return list;
    }

    public List<Integer> postorderPrint(){
        List<Integer> list = new ArrayList();
        Node temp = tree;
        postorderTraversal(temp,list);
        return list;
    }

    public List<Integer> inorderPrint(){
        List<Integer> list = new ArrayList();
        Node temp = tree;
        inorderTraversal(temp,list);
        return list;
    }

    class Node<T>{
        int data;
        Node left;
        Node right;
        public Node(){}
        public Node(int data) {
            this.data = data;
        }
        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }
}
