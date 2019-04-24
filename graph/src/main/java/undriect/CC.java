package undriect;

import abstraction.Graph;

/**
 *  连通分量
 *   与 --- 连通
 *
 * @author Tao
 */
public class CC {
    /**
     * 标记过
     */
    private boolean[] marked;
    /**
     * 连通量
     *   与...连通
     */
    private int[] ids;
    /**
     * 总共有几个连通环
     */
    private int count;

    public CC(Graph graph){
        marked = new boolean[graph.V()];
        ids = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            // 未标记的顶点进行深度优先搜索
            if (!marked[i]){
                dfs(graph,i);
                count++;
            }
        }
    }

    /**
     * 深度优先搜索
     * @param graph
     * @param i
     */
    private void dfs(Graph graph, int i) {
        // 标记访问
        marked[i] = true;
        // 连通标识符
        ids[i] = count;
        for (Integer w : graph.adj(i)) {
            if (!marked[w]){
                dfs(graph,w);
            }
        }
    }

    public boolean connected(int v,int w){
        return ids[v] == ids[w];
    }

    /**
     * 连通环数
     * @return
     */
    public int count() {
        return count;
    }

    /**
     * 连通的标识符
     * @param v
     * @return
     */
    public int id(int v){
        return ids[v];
    }
}
