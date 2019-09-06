package abstraction;


/**
 * 搜索
 */
public abstract class Search {

    /**
     * 搜索的构造方法
     * @param g
     * @param s
     */
    public Search(Graph g, int s){}

    /**
     * 返回v和s是否连同
     * @return
     */
    public abstract boolean marked(int v);

    /**
     * 与s连通的顶点个数
     * @return
     */
    public abstract int count();

}
