import java.io.InputStream;
/**
 * 图
 * @author Tao
 */
public abstract class Graph {

    /**
     * 顶点数
     */
    protected final int V;
    /**
     * 边
     */
    protected int E;
    /**
     * 邻近表
     */
    protected Bag<Integer>[] adjs;
    /**
     *  通过输入流构建图
     * @param in
     */
    public Graph(InputStream in){
        // 读取V并初始化
        this(GraphUtils.readInt(in));
        int E = GraphUtils.readInt(in);
        for (int i = 0; i < E; i++) {
            // 读取一个顶点
            int v = GraphUtils.readInt(in);
            // 另一个顶点
            int w = GraphUtils.readInt(in);
            // 连接它们
            addEdge(v,w);
        }
    }

    /**
     * 创建一个含有N个顶点但不含有边的图
     * @param V
     */
    public Graph(int V){
        // 顶点数
        this.V = V;
        // 边
        this.E = 0;
        // 创建邻近表
        adjs = new Bag[V];
        // 初始化每个邻近表的链表
        for (Bag<Integer> adj : adjs) {
            adj = new Bag<>();
        }
    }

    /**
     * 顶点数
     * @return
     */
    public abstract int V();

    /**
     * 边数
     * @return
     */
    public abstract int E();

    /**
     * 向图增加一条边 v-m
     */
    public abstract void addEdge(int v,int m);

    /**
     * 和v相邻的所有顶点
     * @param v
     * @return
     */
    public abstract Iterable<Integer> adj(int v);

    /**
     * 对象的字符串表示
     * @return
     */
    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(V +" vertices, " + E + " edges \n");
        for (int v = 0; v < V; v++) {
            buffer.append(v + ": ");
            for (Integer w : this.adj(v)) {
                buffer.append( w + " ");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

}
