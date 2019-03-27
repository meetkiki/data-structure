import java.util.Random;

/**
 * 跳表Java实现
 */
public class SkipList {
    /**
     * 最大层数  redis中为32
     */
    private static final int MAX_LEVEL = 16;
    /**
     * 当前最大层数
     */
    private int levelCount = 1;
    /**
     * 带头链表
     */
    private Node head = new Node();
    /**
     * 随机数
     */
    private Random random = new Random();
    /**
     * 集合的数据大小
     */
    private int size = 0;

    /**
     * 随机 level 次，如果是奇数层数 +1，防止伪随机
     */
    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; ++i) {
            if (random.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }
    /**
     * 插入方法
     *  当进行插入操作的时候，程序会维护两个数组，
     *      rank数组保存每层中插入节点前驱前驱节点的排行值，
     *      update数组保存每层插入节点的前驱节点
     * @param value
     */
    public void insert(int value) {
        int level = randomLevel();
        Node newNode = new Node();
        newNode.data = value;
        newNode.maxLevel = level;
        // 插入的位置数组
        Node update[] = new Node[level];
        // 初始化插入位置数组 默认为头结点
        for (int i = 0; i < level; ++i) {
            update[i] = head;
        }

        /**
         * 寻找更新数组的小于插入值--->每个级别的最大值
         *
         *  1.i代表层数，从最高层开始寻找，找到每一层的第一个小于value的最大值
         *  2.在搜索路径update数组中存储更新的位置
         */
        Node p = head;
        for (int i = level - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            // 在搜索路径中使用更新保存节点
            update[i] = p;
        }
        /**
         * 在搜索路径节点中，下一个节点成为新的节点forwords(next)
         */
        for (int i = 0; i < level; ++i) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        // 更新最大层数
        if (levelCount < level) levelCount = level;
        size ++;
    }

    /**
     * 查询方法
     * @param value
     * @return
     */
    public Node find(int value){
        Node temp = head;
        /**
         * 从最高层数据开始遍历,循环levelCount层，每次寻找到索引层forword节点的值不小于目标值时降低索引层
         */
        for (int i = levelCount - 1; i >= 0; i--) {
            /**
             * 当索引节点小于目标值时往前遍历，直到当前搜索节点的forward节点值不再小于目标值
             */
            while (temp.forwards[i] != null && temp.forwards[i].data < value){
               temp = temp.forwards[i];
            }
        }
        // 如果next的值不为空且等于目标值则返回，否则为未找到
        if (temp.forwards[0] != null && temp.forwards[0].data == value) return temp.forwards[0];
        return null;
    }

    /**
     * 删除方法
     * @param value
     * @return
     */
    public boolean delete(int value){
        // 存储需要删除的位置
        Node[] update = new Node[levelCount];
        Node temp = head;
        /**
         * 从最高层数据开始遍历,循环levelCount层，每次寻找到索引层forword节点的值不小于目标值时降低索引层
         */
        for (int i = levelCount - 1; i >= 0; i--) {
            /**
             * 当索引节点小于目标值时往前遍历，直到当前搜索节点的forward节点值不再小于目标值
             */
            while (temp.forwards[i] != null && temp.forwards[i].data < value){
                temp = temp.forwards[i];
            }
            // 存储节点的位置
            update[i] = temp;
        }
        // 如果寻找到目标值则开始删除
        if (temp.forwards[0] != null && temp.forwards[0].data == value){
            for (int i = levelCount - 1; i >= 0; i--) {
                /**
                 * 刪除next的目标节点 删除方法和链表类似 前驱节点的next指针为next节点的next地址
                 */
                if (update[i].forwards[i] != null && update[i].forwards[i].data == value){
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
            size --;
            return true;
        }else{
            return false;
        }
    }

    /**
     * toString方法
     * @return
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        Node node = head.forwards[0];
        while (node != null){
            buffer.append(node.data);
            if (node.forwards[0] != null){
                buffer.append(", ");
            }
            node = node.forwards[0];
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * 立体打印方法
     * @return
     */
    public String printAll(){
        StringBuffer buffer = new StringBuffer();
        int lv = levelCount - 1;
        for (int i = lv; i >= 0; i--) {
            buffer.append("第 " + (i+1) + " 层  ");
            Node temp = head.forwards[i];
            while (temp != null){
                buffer.append(temp.data + "  ");
                if (temp.forwards[i] == null){
                    buffer.append(" \n");
                }
                temp = temp.forwards[i];
            }
        }
        return buffer.toString();
    }

    /**
     *  Node类 节点类
     */
    public class Node {
        /**
         * 存储value
         */
        private int data = -1;
        /**
         * 维护每一层级的指针
         */
        private Node[] forwards = new Node[MAX_LEVEL];
        /**
         * 当前数据的层数
         */
        private int maxLevel = 0;

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", maxLevel=" + maxLevel +
                    '}';
        }
    }



    public static void main(String[] args) {
        SkipList list = new SkipList();
        for (int i = 0; i < 10; i++) {
            list.insert(i);
            System.out.println(list.printAll());
        }
        System.out.println(list);
        System.out.println(list.printAll());
        System.out.println(list.find(5));
        System.out.println(list);
        System.out.println(list.delete(6));
        System.out.println(list);
    }



}
