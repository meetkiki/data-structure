import java.util.ArrayList;
import java.util.List;

/**
 * 红黑树
 *  红黑树的基本思想是用标准的二叉树（完全由2-结点构成）和一些额外的信息（替换3-结点）来表示2-3树
 *  1.红黑树的所有红链接均为左链接
 *  2.没有一个结点同时和两条红链接相连
 *  3.该树是完全红黑平衡的，即任意空链接到根节点的路径上黑结点的个数一样
 */
public class RedBlackBST<K extends Comparable<K>,V> {
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
        h.color = RED;
        h.N = s.N;
        s.N = size(s.right) + size(s.left) + 1;
        return h;
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
        return balance(root);
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return size(root) == 0;
    }

    /**
     * 删除最小键
     *  如果左右子节点都为黑色，那么将这三个结点合并为4结点
     */
    public void deleteMin(){
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    /**
     * 删除最小键
     *  1.根节点的左结点为树的底部 直接删除它
     *  2.删除的主要思想是从树底部的3-结点删除(参考2-3-4树)，而2-结点删除会破坏平衡性，所以有两种情况
     *      1.如果根节点和它的两个子节点都为2-结点，我们可以把它变为临时的4-结点；
     *      2.需要保证左结点不为2-结点，所以就需要从兄弟结点“借”过来作为左结点,将其变为3-或者临时的4-结点
     *          ①如果当前结点的左子节点为2-结点而它的亲兄弟结点不是2-结点，则使用移动“借”其兄弟结点
     *          ②如果当前结点和它的亲兄弟结点都为2-结点，将左子节点、
     *          父结点中的最小键和左子节点最近的兄弟结点合并为一个4-结点，使父节由3-结点变为2-结点或者由4-结点变为3-结点
     * @param root
     * @return
     */
    private Node deleteMin(Node root) {
        if (root.left == null) return null;
        // 如果左子节点和它的左子节点都为黑结点 我们需要从它的右侧的兄弟节点中"借"来
        if (!isRed(root.left) && !isRed(root.left.left))
            root = moveRedLeft(root);
        root.left = deleteMin(root.left);
        // 维持平衡性
        if (isRed(root.right)){
            root = rotateLeft(root);
        }
        return balance(root);
    }

    /**
     * 删除最大键
     *  思路和删除最小键一致，细节是由于红链接均为左链接
     */
    public void deleteMax(){
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    /**
     * 将节点的右结点或者右子结点变红
     * @param root
     * @return
     */
    private Node moveRedRight(Node root) {
        moveflipColors(root);
        if (root.left != null && !isRed(root.left.left)){
            root = rotateRight(root);
        }
        return root;
    }

    /**
     * 删除最大键
     * @param root
     * @return
     */
    private Node deleteMax(Node root) {
        // 刪除最大值主要是删除右结点 如果左结点为红结点 则从左结点“借”
        if (isRed(root.left)){
            root = rotateRight(root);
        }
        if (root.right == null) return null;
        // 如果右结点和它的子节点都为黑结点 那就从左子节点“借”一个节点
        if (!isRed(root.right) && !isRed(root.right.left)){
            root = moveRedRight(root);
        }
        root.right = deleteMax(root.right);
        // 维持平衡性
        if (isRed(root.right)){
            root = rotateLeft(root);
        }
        return balance(root);
    }

    /**
     * 将节点的左结点或者左子结点变红
     *  如果兄弟节点的左结点为红链接，则将其“借”过来作为左结点
     * @param root
     * @return
     */
    private Node moveRedLeft(Node root) {
        moveflipColors(root);
        // 如果右键为3-结点，则借键给左孩子
        if (isRed(root.right.left)){
            root.right = rotateRight(root.right);
            root = rotateLeft(root);
        }
        return root;
    }

    /**
     * 将左右子节点变为红色 * 根节点变为黑色 （删除）
     *                     \                                \ --- 黑链接
     *                     s                                 s
     *       黑链接  ---  /  \  --- 黑链接      红链接  ---  /  \ --- 红链接
     *                   h   r                             h   r
     * @param h
     */
    public void moveflipColors(Node h){
        h.color = BLACK;
        if (h.left != null) h.left.color = RED;
        if (h.right != null) h.right.color = RED;
    }


    /**
     * 插入或者删除调整结点的平衡性
     * @param root
     * @return
     */
    private Node balance(Node root) {
        // ①如果当前根节点的右子链接为红色，左子链接为黑色，则执行左旋
        if (isRed(root.right) && !isRed(root.left)) root = rotateLeft(root);
        // ②如果当前根节点的左子链接为红色，左子链接的左子链接，则执行右旋
        if (isRed(root.left) && isRed(root.left.left)) root = rotateRight(root);
        // ③如果当前根节点的左右子链接均为红色，则改变颜色（将此步放在第①步则可以将2-3树红黑树变为2-3-4树插入的平衡调整）
        if (isRed(root.left) && isRed(root.right)) flipColors(root);
        root.N = size(root.right) + size(root.left) + 1;
        return root;
    }

    /**
     *  删除
     *      将删除最大最小合并就是删除方法
     * @param key
     */
    public void delete(K key){
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = delete(root,key);
        if (!isEmpty()) root.color = BLACK;
    }

    /**
     * 返回最小节点
     * @return
     */
    public Node min(){
        if (root == null) return null;
        return min(root);
    }

    private Node min(Node root) {
        if (root.left == null) return root;
        return min(root.left);
    }

    /**
     * 红黑树删除实现
     * @param root
     * @return
     */
    private Node delete(Node root,K key) {
        if (root == null) return null;
        if (root.key.compareTo(key) > 0){
            // 向左查找 删除小键
            // 如果左子节点和它的左子节点都为黑结点 我们需要从它的右侧的兄弟节点中"借"来
            if (!isRed(root.left) && !isRed(root.left.left))
                root = moveRedLeft(root);
            root.left = delete(root.left,key);
        }else{
            // 向右查找 删除大键
            // 刪除最大值主要是删除右结点 如果左结点为红结点 则从左结点“借”
            if (isRed(root.left)){
                root = rotateRight(root);
            }
            if (root.key.compareTo(key) == 0 && root.right == null) return null;
            // 如果右结点和它的子节点都为黑结点 那就从左子节点“借”一个节点
            if (!isRed(root.right) && !isRed(root.right.left)){
                root = moveRedRight(root);
            }
            if (root.key.compareTo(key) == 0){
                // 删除方法 找到当前右子树最小结点 作为替代结点
                Node n = min(root.right);
                root.value = n.value;
                root.key = n.key;
                root.right = deleteMin(root.right);
            }else{
                root.right = delete(root.right,key);
            }
        }
        // 维持平衡性
        if (isRed(root.right)){
            root = rotateLeft(root);
        }
        return balance(root);
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
