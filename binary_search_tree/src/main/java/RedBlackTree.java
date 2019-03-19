import java.util.ArrayList;
import java.util.List;

/**
 * 红黑树
 *  红黑树的基本思想是用标准的二叉树（完全由2-结点构成）和一些额外的信息（替换3-结点）来表示2-3树
 *  1.红黑树的所有红链接均为左链接
 *  2.没有一个结点同时和两条红链接相连
 *  3.该树是完全红黑平衡的，即任意空链接到根节点的路径上黑结点的个数一样
 */
public class RedBlackTree<K extends Comparable<K>,V> {
    /**
     * 如果链接为红色则为true，黑色为false
     */
    public static final boolean RED = true;
    public static final boolean BLACK = false;

    private Node root;

    class Node<Key extends Comparable<K>,V>{
        /**
         * 键和值
         */
        Key key;
        V value;
        /**
         * 左右子节点
         */
        Node left;
        Node right;
        /**
         * 当前节点的子节点总个数
         */
        int N;
        /**
         * 由其父节点指向它的链接颜色
         */
        boolean color;

        public Node() {}
        public Node(Key key, V value, int n, boolean color) {
            this.key = key;
            this.value = value;
            N = n;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", N=" + N +
                    ", color=" + color +
                    '}';
        }
    }

    /**
     * 给定节点判断与其父链接是否为红色
     * @param x
     * @return
     */
    private boolean isRed(Node x){
        if (x == null) return false;
        return x.color == RED;
    }

    /**
     * 返回当前容量
     * @return
     */
    public int size(){
        return size(root);
    }

    private int size(Node node){
        if (node == null) return 0;
        return node.N;
    }

    /**
     *  左旋操作:将红色的右链接转化为红色的左链接过程
     *     \                                        \
     *      h                                        s
     *    /  \ --- 红链接      --->     红链接  ---  / \
     *   j    s                                    h   r
     *       / \                                 /  \
     *      l   r                               j   l
     *   只是将用两个键中的较小者作为根节点变为较大者作为根节点
     *    1.取出当前节点的右子节点s作为根节点，s根节点的右结点不变
     *    2.原根节点的左结点数据不变，右结点为s的左结点
     *    3.新根结点的父链接颜色为原节点的父链接颜色，原根节点的颜色变为红色
     *    4.新节点子结点数为原节点的结点总数，原结点的子节点总数重新计算
     *    5.右旋操作与此同理，只需要将left与right交换即可
     * @param h
     * @return
     */
    public Node rotateLeft(Node h){
        Node s = h.right;
        h.right = s.left;
        s.left = h;
        s.color = h.color;
        h.color = RED;
        s.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return s;
    }
    /**
     *  右旋操作:将红色的左链接转化为红色的右链接过程
     *               \                 \
     *               s                 h
     * 红链接  ---  /  \              /  \   --- 红链接
     *             h   r            j    s
     *           /  \                   / \
     *          j    l                 l   r
     *
     * @param s
     * @return
     */
    public Node rotateRight(Node s){
        Node h = s.left;
        s.left = h.right;
        h.right = s;
        h.color = s.color;
        s.color = RED;
        h.N = s.N;
        s.N = size(s.right) + size(s.left) + 1;
        return s;
    }

    /**
     * 将左右子节点变为黑色 * 根节点变为红色
     *                     \                                \ --- 红链接
     *                     s                                 s
     *       红链接  ---  /  \  --- 红链接      黑链接  ---  /  \ --- 黑链接
     *                   h   r                             h   r
     * @param h
     */
    public void flipColors(Node h){
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 查询方法
     * @param key
     */
    public V get(K key){
        return get(root,key);
    }

    /**
     * 获取方法 和二分查找一样
     * @param root
     * @param key
     * @return
     */
    private V get(Node root, K key) {
        if (root == null) return null;
        /**
         * root.key > key    1
         * root.key < key    -1
         * root.key > key    0
         */
        int compare = root.key.compareTo(key);
        if (compare > 0) return (V)get(root.left,key);
        else if (compare < 0) return (V)get(root.right,key);
        else return (V)root.value;
    }


    /**
     * 插入方法
     * @param key
     * @param val
     */
    public void put(K key,V val){
        root = put(root,key,val);
        root.color = BLACK;
    }

    /**
     * 插入方法的递归实现
     * 1.如果当前根节点的右子链接为红色，左子链接为黑色，则执行右旋
     * 2.如果当前根节点的左子链接为红色，左子链接的左子链接，则执行右旋
     * 3.如果当前根节点的左右子链接均为红色，则改变颜色
     * @param root
     * @param key
     * @param val
     * @return
     */
    private Node put(Node root, K key, V val) {
        if (root == null) return new Node(key,val,1,RED);
        /**
         * root.key > key    1
         * root.key < key    -1
         * root.key > key    0
         */
        int compare = root.key.compareTo(key);
        if (compare > 0) root.left = put(root.left,key,val);
        else if (compare < 0) root.right = put(root.right,key,val);
        else root.value = val;
        // 如果当前根节点的右子链接为红色，左子链接为黑色，则执行左旋
        if (isRed(root.right) && !isRed(root.left)) root = rotateLeft(root);
        // 如果当前根节点的左子链接为红色，左子链接的左子链接，则执行右旋
        if (isRed(root.left) && isRed(root.left.left)) root = rotateRight(root);
        // 如果当前根节点的左右子链接均为红色，则改变颜色
        if (isRed(root.left) && isRed(root.right)) flipColors(root);
        root.N = size(root.right) + size(root.left) + 1;
        return root;
    }

    /**
     * 中序遍历
     * @return
     */
    public String inorderTraversal(){
        List<String> list = new ArrayList<>();
        inorderTraversal(root,list);
        return list.toString();
    }

    private void inorderTraversal(Node root, List<String> list) {
        if (root == null) return;
        inorderTraversal(root.left,  list);
        list.add(root.toString());
        inorderTraversal(root.right,  list);
    }

    /**
     * 前序遍历
     * @return
     */
    public String preorderTraversal(){
        List<String> list = new ArrayList<>();
        preorderTraversal(root,list);
        return list.toString();
    }

    private void preorderTraversal(Node root, List<String> list) {
        if (root == null) return;
        list.add(root.toString());
        inorderTraversal(root.left,  list);
        inorderTraversal(root.right,  list);
    }


}
