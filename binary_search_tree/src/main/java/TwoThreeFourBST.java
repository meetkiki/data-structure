import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * 2-3-4平衡查找树
 */
public class TwoThreeFourBST<T> {

    /**
     * 根节点
     */
    Node root = new Node();

    /**
     * 根据key查询方法
     *  1.根据键key值在当前节点中查找，如果找到则返回
     *  2.如果当前节点中未找到则在child中查询，getNextChild返回子节点的数据
     * @param key
     * @return
     */
    public DataItem find(long key){
        Node currNode = root;
        while (currNode != null){
            DataItem item = currNode.findItem(key);
            if (item != null){
                return item;
            }else if(currNode.isLeaf()){
                // 如果叶子节点也没有
                return null;
            }else{
                // 获取下一个查询节点
                currNode = currNode.getNextChild(key);
            }
        }
        return null;
    }

    /**
     * 插入节点数据
     *  1.与find方法类似，不断向下查找找到叶节点，插入数据项
     *  2.判断当前插入节点是否是满节点，如果是则先进行分裂操作，再插入数据项操作
     * @param dataItem
     */
    public void insert(DataItem dataItem){
        Node currNode = root;
        long key = dataItem.key;
        while (true){
            if (currNode.isFull()){
                // 如果是满节点 先进行分裂操作
                split(currNode);
                // 回到分裂出的父节点上
                currNode = currNode.getParent();
                // 向下查找
                currNode = currNode.getNextChild(key);
            }else if(currNode.isLeaf()){
                // 是叶节点 非满节点
                break;
            }else{
                // 向下查找
                currNode = currNode.getNextChild(key);
            }
        }
        currNode.insertData(dataItem);
    }


    /**
     * 分裂节点
     * 一、处理节点为根节点
     *      (A B C)     ----处理节点
     *     /  | \  \
     *    1   2  3  4
     *
     *  1.创建一个新的节点，作为要分裂兄弟的父结点
     *  2.再创建一个新的节点，作为要分裂的兄弟右节点，位于节点的右侧
     *  3.将数据C移动到创建的兄弟节点，放在右侧
     *  4.将数据B移动到创建的父节点,数据A保留原地
     *  5.将需要分裂的节点最右边两个子节点断开连接，重新拼接在兄弟节点上
     * 二、处理节点不为根节点
     *                 (50)
     *              /       \
     *           (20)      (A , B , C)   ----处理节点
     *          /  \       /  |   \   \
     *        (15) (22) (55) (62) (71) (83)
     *  1.创建一个新节点，与要分裂的节点是兄弟节点，放在其右侧
     *  2.将数据C放入新建节点，将数据B移动到父节点的相应位置
     *  3.数据节点A保留原来的位置
     *  4.将原节点最右边的两个子节点（原节点为满节点一定有四个子节点或叶节点）从要分裂的地方断开，
     *      连接到新建的兄弟节点上
     *
     * @param node
     */
    public void split(Node node){
        Node parent;
        // 存储当前节点的数据项
        DataItem itemC = node.removeItem();
        DataItem itemB = node.removeItem();
        // 断开子节点的连接
        Node childB = node.disconnectChild(2);
        Node childC = node.disconnectChild(3);
        // 创建一个新节点 作为右结点
        Node newRight = new Node();
        /**
         * 获取父节点
         *  如果当前节点是根节点
         */
        if (node == root){
            // 创建一个新的根节点
            root = new Node();
            // 新节点为父节点
            parent = root;
            // 拼接当前节点
            root.connectChild(0,node);
        }else{
            /**
             * 不是根节点则赋值当前节点
             */
            parent = node.getParent();
        }
        /**
         *  将数据itemB插入到父节点中，并重新建立子节点的联系
         */
        // 将节点B插入父节点 返回父节点的索引位置
        int insertIndex = parent.insertData(itemB);
        int numItems = parent.getNumItems();
        /**
         * 更新右兄弟节点的连接
         */
        for (int i = numItems - 1; i > insertIndex; i--) {
            // 断开以前的父亲节点的连接
            Node temp = parent.disconnectChild(i);
            parent.connectChild(i+1,temp);
        }
        // 将新节点与父亲节点连接
        parent.connectChild(insertIndex+1,newRight);
        // 处理兄弟节点C
        newRight.insertData(itemC);
        // 将B和C的子节点关联在兄弟节点上
        newRight.connectChild(0,childB);
        newRight.connectChild(1,childC);
    }

    /**
     * 展示所有节点数据
     */
    public void displayTree(){
        resDisplayTree(root,0,0);
    }

    /**
     * 绘制树
     * @param root
     * @param level
     * @param childNum
     * @return
     */
    private void resDisplayTree(Node root, int level, int childNum) {
        System.out.println("level="+level+" child="+childNum+" ");
        String item = root.displayItem();
        System.out.println(item);
        int numItems = root.getNumItems();
        for (int i = 0; i < numItems + 1; i++) {
            Node child = root.getChild(i);
            if (child!=null){
                resDisplayTree(child,level+1,i);
            }
        }
        return;
    }


    /**
     * 绘制2-3-4树
     */
    public void printTree() {
        MyWindow jf = new MyWindow();
        jf.setSize(1000, 1000);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(3);
        this.drawTree(root, jf, 395, 100, 1);
    }

    /**
     * 绘制Node的数据节点矩形
     * @param node 数据节点
     * @param x    首节点坐标x
     * @param y    首节点坐标y
     * @return
     */
    public List<Area> nodoToArea(Node node,int x,int y){
        List<Area> list = new ArrayList<>();
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            list.add(new Area(x + (i - 1) * 30,y - 20, 30, 20));
        }
        return list;
    }
    /**
     * 绘制Node的数据
     * @param node 数据节点
     * @param x    首节点坐标x
     * @param y    首节点坐标y
     * @return
     */
    public List<Word> nodoToWord(Node node,int x,int y){
        List<Word> list = new ArrayList<>();
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            list.add(new Word(String.valueOf(node.itemArray[i].key), x + 30 * i - 20, y - 5));
        }
        return list;
    }

    /**
     * 画2-3-4搜索树
     *  TODO 待改进
     */
    public void drawTree(Node node, MyWindow jf, int x, int y, int level) {
        Graphics g = jf.getGraphics();
        jf.getShapes().add(new Word("第"+level+"层",15,y));
        level++;
        if (node != null){
            jf.getShapes().addAll(nodoToArea(node, x, y));
            jf.getShapes().addAll(nodoToWord(node, x, y));
        }
        if (!node.isLeaf()) {
            // 划线
            int childSize = node.getChildSize(node),xl = 10,yl = -10;
            switch (childSize){
                case 1:
                    // 只需要画左条链线
                    jf.getShapes().add(new Line(x - xl, y, x - 150/level + xl, y + 10*level + yl));
                    break;
                case 2:
                    // 只需要画左右链线
                    jf.getShapes().add(new Line(x - xl, y, x - 150/level + xl, y + 10*level + yl));
                    jf.getShapes().add(new Line(x + childSize * xl, y, x + 150/level + xl, y + 10*level + yl));
                    break;
                case 3:
                    // 左右链线加中线
                    jf.getShapes().add(new Line(x - xl, y, x - 150/level + xl, y + 10*level + yl));
                    jf.getShapes().add(new Line(x, y, x, y + 10*level + yl));
                    jf.getShapes().add(new Line(x + childSize * xl, y, x + 150/level + xl, y + 10*level + yl));
                    break;
                case 4:
                    // 左右链线加两条中线
                    jf.getShapes().add(new Line(x - 2*xl, y, x - 150/level + xl, y + 10*level + yl));
                    jf.getShapes().add(new Line(x - xl, y, x- xl, y + 10*level + yl));
                    jf.getShapes().add(new Line(x + xl, y, x+ xl, y + 10*level + yl));
                    jf.getShapes().add(new Line(x + 2*xl, y, x + 150/level + xl, y + 10*level + yl));
                    break;
                default: break;
            }
            for (int i = 0; i < childSize; i++) {
                drawTree(node.childArray[i], jf, x - (i) * 150/level - xl  , y + 10*level - yl, level);
            }
        }
    }


    /**
     * 存储的数据类型
     *  自定义对象
     *  key long 类型
     * @param <T>
     */
    public static class DataItem<T>{
        private long key;
        private T data;
        public long getKey() {
            return key;
        }
        public void setKey(long key) {
            this.key = key;
        }
        public T getData() {
            return data;
        }
        public void setData(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "{" +
                    "key=" + key +
                    ", data=" + data +
                    '}';
        }
        public DataItem(T data) {
            this.data = data;
            this.key = data.hashCode();
        }
    }


    /**
     * 结点类
     *  1.表示结点中存储数据的方式
     *      包括两个数组类型，childArray和ItemArray。childArray有四个数据单元，
     *         来存储子节点，ItemArray有三个存储单元用于存储DataItem对象(的引用)
     *  2.主要有三个方法
     *      ①根据关键key在itemArray中查找
     *      ②insertItem 插入到ItemArray中,并保持有序（根据关键key大小排序）
     *      ③removeItem 根据关键key删除数据，并保持有序
     */
    class Node<T>{
        /**
         * 子节点数组大小
         */
        private static final int ORDER = 4;
        /**
         * 当前节点中存储的数据，不大于3
         */
        private int numItems;
        /**
         * 父节点
         */
        private Node parent;
        /**
         * 数据数组
         */
        private DataItem<T>[] itemArray = new DataItem[ORDER - 1];
        /**
         * 子节点引用数组
         */
        private Node<T>[] childArray = new Node[ORDER];

        /**
         *  将参数的结点作为子节点拼接
         * @param childIndex
         * @param child
         */
        public void connectChild(int childIndex,Node child){
            childArray[childIndex] = child;
            if (child != null){
                child.parent = this;
            }
        }

        /**
         * 断开参数确定的节点与当前节点的连接，这个节点一定是当前节点的子节点。
         */
        public Node disconnectChild(int childIndex){
            Node tempNode = childArray[childIndex];
            //断开连接
            childArray[childIndex] = null;
            //返回要这个子节点
            return tempNode;
        }

        /**
         * 获取子节点
         * @param childIndex
         * @return
         */
        public Node getChild(int childIndex){
            return childArray[childIndex];
        }

        /**
         * 获取当前节点的子节点个数
         * @param node
         * @return
         */
        public int getChildSize(Node node){
            Node[] childArray = node.childArray;
            int i = 0;
            for (; i < childArray.length; i++) {
                if (childArray[i] == null){
                    return i;
                }
            }
            return i;
        }
        /**
         * 获取父节点
         * @return
         */
        public Node getParent() {
            return parent;
        }

        /**
         * 获取实际叶节点的存储数目
         * @return
         */
        public int getNumItems() {
            return numItems;
        }

        /**
         * //是否是叶结点
         * @return
         */
        public boolean isLeaf(){
            //叶结点没有子节点
            return (childArray[0]==null) ? true : false;
        }

        /**
         * 判断当前节点是否已满
         * @return
         */
        public boolean isFull(){
            return (numItems == ORDER - 1) ? true : false;
        }

        /**
         * 根据key查询
         * @param key
         * @return
         */
        public DataItem findItem(long key){
            for (int i = 0; i < ORDER - 1; i++) {
                // 如果数组未满未找到直接返回
                if (itemArray[i] == null){
                    break;
                }else if (itemArray[i].key == key){
                    return itemArray[i];
                }
            }
            return null;
        }

        /**
         * 节点未满时插入数据方法
         * @param dataItem
         * @return
         */
        public int insertData(DataItem dataItem){
            numItems++;
            long key = dataItem.key;
            // 从数据节点的右边第二个开始查找 //未满则代表最少有一个位置 itemArray 1 1 1
            for (int i = ORDER - 2; i >= 0; i--) {
                if (itemArray[i] == null){
                    continue;
                }else{
                    // 获取当前数据key
                    long itKey = itemArray[i].key;
                    if (itKey > key){
                        // 如果key小于当前key 将当前数据后移
                        itemArray[i + 1] = itemArray[i];
                    }else{
                        // 否则在当前节点后插入
                        itemArray[i + 1] = dataItem;
                        return i + 1;
                    }
                }
            }
            /**
             * 插入第一个节点的位置
             *  1.如果初始节点为null则上面什么都不做，则会插入在第一个节点的位置
             *  2.如果上面的操作都当前key小于当前节点中的所有key 仍然为插入第一个节点的位置
             */
            itemArray[0] = dataItem;
            return 0;
        }

        /**
         * 获取查询的下一个子节点
         *   示例
         *      (10      20      30）            k   ---层
         *     /|     / |    | \     | \        k+1  ---层
         *   (5 6)  (12 15) (22 23) (31 33)
         *      查找15时返回(12,15)子节点，
         *      查询35时返回(31,33)子节点
         * @param theKey  查找的key
         * @return
         */
        public Node getNextChild(long theKey){
            int i = 0;
            // 当前节点的个数
            int numItems = this.numItems;
            for (; i < numItems; i++) {
                // 如果查询的结点key大于当前节点的值 则向下查找
                if (this.itemArray[i].key > theKey){
                    return this.childArray[i];
                }
            }
            // 如果查询的节点key始终小于当前key 即从最右节点查找 i == numItems
            return this.childArray[i];
        }

        /**
         * 从后往前移除结点
         * @return
         */
        public DataItem removeItem(){
            if (numItems == 0)
                return null;
            numItems --;
            DataItem dataItem = itemArray[numItems];
            itemArray[numItems] = null;
            return dataItem;
        }

        /**
         * 打印当前节点的数据
         * @return
         */
        public String displayItem(){
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            for (int i = 0; i < numItems; i++) {
                buffer.append(itemArray[i]);
                if (i != (numItems - 1)){
                    buffer.append(", ");
                }
            }
            buffer.append("]");
            return buffer.toString();
        }

    }

}
