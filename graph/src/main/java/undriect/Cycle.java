package undriect;

import abstraction.Graph;

public class Cycle {

    /**
     * 标记过
     */
    private boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph graph){
        marked = new boolean[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            // 未标记的顶点进行深度优先搜索
            if (!marked[i]){
                dfs(graph,i,i);
            }
        }
    }

    private void dfs(Graph graph, int i,int u) {
        // 标记访问
        marked[i] = true;
        for (Integer w : graph.adj(i)) {
            if (!marked[w]){
                dfs(graph,w,i);
            }else if (w != u){
                hasCycle = true;
            }
        }
    }

    public boolean isHasCycle() {
        return hasCycle;
    }
}
