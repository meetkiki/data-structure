package undriect;

import abstraction.Graph;
import abstraction.Paths;
import common.Bag;

/**
 *  深度优先搜索
 *
 * @author Tao
 */
public class DepthFirstPaths extends Paths {

    /**
     * 标记能否通过
     */
    protected boolean[] marked;
    /**
     * 从起点到已知路径上最后一点
     */
    protected int[] edgeTo;
    /**
     * 当前节点
     */
    protected final int s;

    /**
     * 深度优先搜索
     * @param graph
     * @param s
     */
    public DepthFirstPaths(Graph graph, int s) {
        super(graph,s);
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        this.s = s;
        dfs(graph,s);
    }

    /**
     * 深度优先搜索查询路径
     * @param graph
     * @param s
     */
    private void dfs(Graph graph, int s) {
        marked[s] = true;
        for (Integer w : graph.adj(s)) {
            if (!marked[w]){
                edgeTo[w] = s;
                dfs(graph,w);
            }
        }
    }

    /**
     * s与v连通否
     * @param v
     * @return
     */
    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * s到v的路径
     * @param v
     * @return
     */
    @Override
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Bag<Integer> bag = new Bag<>();
        for (int i = v; i != s; i = edgeTo[i]) {
            bag.addFirst(i);
        }
        bag.addFirst(s);
        return bag;
    }
}
