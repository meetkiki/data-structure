package core;

import entity.DirectedTrip;
import entity.Town;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ypt
 * @ClassName AbstractSearch
 * @Description 抽象搜索类
 * @date 2019/9/6 11:18
 */
public abstract class AbstractSearch{
    
    /**
     * 图信息
     */
    private final AbstractGraph graph;
    /**
     * 源地址
     */
    private final Town from;
    /**
     * 最短路径树中的边,这里使用一个哈希表标识
     *   比如由 A->B 存在最短路径
     *   那么 存在 B --> AB 的边 也就是在A到B路上最后的一条边
     */
    private final Map<Town,DirectedTrip> tripsTo;
    /**
     * 到达from点的距离,这里使用一个哈希表标识
     *    A --> 0      表示原点为0
     *    B --> 5      表示原点到点B的距离为5
     *    ...
     */
    private final Map<Town, BigDecimal> distsTo;
    
    /**
     * 构造函数
     * @param graph  图
     * @param from   小镇
     */
    public AbstractSearch(Digraph graph, Town from){
        this.graph = graph;
        this.from = from;
        // 初始化最小路径的数据结构
        this.tripsTo = new ConcurrentHashMap<>();
        this.distsTo = new ConcurrentHashMap<>();
    }
    
    /**
     * 从顶点from到to的距离 如果不存在则为null
     * @param to 目标地
     * @return   距离
     * @throws IllegalArgumentException 不存在则抛出IllegalArgumentException异常
     */
    public abstract Double distTo(Town to);
    
    /**
     * 是否存在从from到to的路径
     * @param to 目标地
     * @return
     * @throws IllegalArgumentException to不存在则抛出IllegalArgumentException异常
     */
    public abstract boolean hasPathTo(Town to);
    
    /**
     * 从顶点from到to的路径 如果不存在则为null
     * @param to 目标地
     * @return
     * @throws IllegalArgumentException to不存在则抛出IllegalArgumentException异常
     */
    public abstract Collection<DirectedTrip> pathTo(Town to);
    
    /**
     * 边的松弛 -- 尝试寻找当前传入的边,查看目标地的距离是否小于当前最小路径
     *
     * 比如
     *     A - > B - > C        10
     *     A - > E - > F -> C   20
     *  那么此时传入BC边,这时就会替换掉原有的FC边 且更新最短路径为10
     *
     *  @param trip 尝试的边
     */
    protected void relax(DirectedTrip trip){
        Town from = trip.getFrom(),to = trip.getTo();
        // 如果原点从边from -> to 到达to的距离小于当前的distsTo[to](最短路径)
        // 那么替换最短路径数据
        BigDecimal dist = distsTo.get(from).add(trip.getDistance());
        if (distsTo.get(to).compareTo(dist) >= 0){
            // 更新距离
            distsTo.put(to,dist);
            tripsTo.put(to,trip);
        }
    }
    
    /**
     * 顶点的松弛 这里指的是上述操作往往会持续一个点的所有边,即由这个顶点指出的所有边
     * @param from 探测地点
     */
    protected void relax(Town from){
        // 拿到由to指出
        for (DirectedTrip trip : graph.adj(from)) {
            // 松弛所有指出边
            relax(trip);
        }
    }
    
    
    public AbstractGraph getGraph() {
        return graph;
    }
    
    public Town getFrom() {
        return from;
    }
}
