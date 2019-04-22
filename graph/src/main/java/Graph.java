import java.io.InputStream;

/**
 * 图
 */
public abstract class Graph {
    /**
     *  通过输入流构建图
     * @param in
     */
    public Graph(InputStream in){}

    /**
     * 创建一个含有N个顶点但不含有边的图
     * @param V
     */
    public Graph(int V){}

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
    public abstract String toString();

}
