package core;

import common.Constant;
import entity.DirectedTrip;
import entity.Town;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
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
    protected final AbstractGraph graph;
    /**
     * 源地址
     */
    protected final Town from;
    /**
     * 最短路径树中的边,这里使用一个哈希表标识
     *   比如由 A->B 存在最短路径
     *   那么 存在 B --> AB 的边 也就是在A到B路上最后的一条边
     *   如果是自身from 那么是null
     */
    protected final Map<Town,DirectedTrip> tripsTo;
    /**
     * 到达from点的距离,这里使用一个哈希表标识
     *    A --> 0      表示原点为0
     *    B --> 5      表示原点到点B的距离为5
     *    ...
     *    如果是自身from  那么是0
     */
    protected final Map<Town, BigDecimal> distsTo;
    
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
     * 从顶点from到to的距离
     *  如果from到to不可达 则为null 这里则没有任何意义
     * @param to 目标地
     * @return   距离
     * @throws IllegalArgumentException 不存在则抛出IllegalArgumentException异常
     */
    public BigDecimal distTo(Town to){
        checkTown(to);
        return distsTo.get(to);
    }
    
    /**
     * 返回是否存在从from到to的路径
     * @param to 目标地
     * @return
     * @throws IllegalArgumentException to不存在则抛出IllegalArgumentException异常
     */
    public boolean hasPathTo(Town to){
        checkTown(to);
        return Constant.NoSuch_Max_Dist.compareTo(distsTo.get(to)) > 0;
    }
    
    /**
     * 校验参数
     * @param to
     */
    protected void checkTown(Town to) {
        if (!this.graph.hasTown(to)){
            throw new IllegalArgumentException(String.format(" to -> %s does not exist !",to));
        }
    }
    
    /**
     * 从顶点from到to的路径 如果不存在则为null
     * @param to 目标地
     * @return   返回路径集合
     * @throws IllegalArgumentException to不存在则抛出IllegalArgumentException异常
     */
    public Collection<DirectedTrip> pathTo(Town to){
        if (!hasPathTo(to)) return null;
        LinkedList<DirectedTrip> paths = new LinkedList<>();
        // 从目标地出发 反查询地址
        for (DirectedTrip trip = tripsTo.get(to); trip != null; trip = tripsTo.get(trip.getFrom())) {
            paths.addFirst(trip);
        }
        return paths;
    }
    
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
        for (DirectedTrip trip : graph.adj(from).keySet()) {
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
