/**
 * 深度优先搜索
 */
public class DepthFirstSearch extends Search {

    private boolean marked[];
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
            // 如果未搜索 执行搜索
            if (!marked[w]){
                dfs(g,s);
            }
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
