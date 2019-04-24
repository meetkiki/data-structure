package undriect;

import abstraction.Graph;
import abstraction.Search;

/**
 * 深度优先搜索
 * @author Tao
 */
public class DepthFirstSearch extends Search {

    /**
     * 标记能否通过
     */
    private boolean[] marked;
    /**
     * 顶点数
     */
    private int count;

    public DepthFirstSearch(Graph g, int s) {
        super(g,s);
        marked = new boolean[g.V()];
        dfs(g,s);
    }

    /**
     * 深度优先搜索
     * @param g
     * @param s
     */
    private void dfs(Graph g, int s) {
        marked[s] = true;
        count++;
        // 与s相连的所有顶点
        for (Integer w : g.adj(s)) {
            // 如果未搜索 执行搜索 判断与当前节点是否连通
            if (!marked[w]) dfs(g,w);
        }
    }

    /**
     * 返回v和s是否连同
     * @param v
     * @return
     */
    @Override
    public boolean marked(int v) {
        return marked[v];
    }

    /**
     * 与s相连的顶点数
     * @return
     */
    @Override
    public int count() {
        return count;
    }
}
