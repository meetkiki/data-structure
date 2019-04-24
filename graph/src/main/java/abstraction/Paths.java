package abstraction;

/**
 * 寻找路径
 * @author Tao
 */
public abstract class Paths {

    /**
     * 构造方法
     *  在graph中寻找s的所有路径
     * @param graph
     * @param s
     */
    public Paths(Graph graph, int s){}

    /**
     * 是否存在从s到v的路径
     * @param v
     * @return
     */
    public abstract boolean hasPathTo(int v);

    /**
     * 从s到v的路径
     * @param v
     * @return
     */
    public abstract Iterable<Integer> pathTo(int v);
}
