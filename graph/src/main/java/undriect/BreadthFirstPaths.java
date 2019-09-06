package undriect;

import abstraction.Graph;
import abstraction.Paths;
import common.Bag;

import java.util.ArrayDeque;

/**
 * 广度优先搜索
 * @author Tao
 */
public class BreadthFirstPaths extends Paths {
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
     * 广度优先搜索
     * @param graph
     * @param s
     */
    public BreadthFirstPaths(Graph graph, int s) {
        super(graph, s);
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        this.s = s;
        bfs(graph,s);
    }

    /**
     * 广度优先搜索
     * @param graph
     * @param s
     */
    private void bfs(Graph graph, int s) {
        // 根据最近出现的距离弹出 所有用队列表示
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        // 标记s
        marked[s] = true;
        queue.add(s);
        while (!queue.isEmpty()){
            // pop出v，处理它的邻近顶点 如果没有标记则记录距离
            Integer v = queue.pop();
            for (Integer w : graph.adj(v)) {
                if (!marked[w]){
                    // edgeTo 表示w到达v 保存最短路径的一条边
                    edgeTo[w] = v;
                    // 标记已处理
                    marked[w] = true;
                    // 并将其加入队列
                    queue.add(w);
                }
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
