import java.io.InputStream;

/**
 * 无向图
 * @author Tao
 */
public class UndirectedGraph extends Graph{

    public UndirectedGraph(InputStream in) {
        super(in);
    }

    public UndirectedGraph(int V) {
        super(V);
    }

    /**
     * 顶点数
     * @return
     */
    @Override
    public int V() {
        return V;
    }

    /**
     * 边数目
     * @return
     */
    @Override
    public int E() {
        return E;
    }

    /**
     * 增加一条边
     * @param v
     * @param w
     */
    @Override
    public void addEdge(int v, int w) {
        adjs[v].add(w);
        adjs[w].add(v);
        E++;
    }

    /**
     * 返回v处的bag链表
     * @param v
     * @return
     */
    @Override
    public Iterable<Integer> adj(int v) {
        return adjs[v];
    }

}
