package core;

import common.Constant;
import entity.DirectedTrip;
import entity.Town;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最短路径的Dijkstra算法
 *  算法思路：
 *      1.首先将点from能到达的距离设置到distsTo，其他为正无穷（这里暂时不考虑负距离问题）
 *      2.维护一个优先队列，队列的值为每个小镇到from点的距离，按照默认比较器排序或者传入比较器（传入不同比较器解决最近/远路径）
 *      3.不断将其他点加入优先队列，直到所有点都在最小路径树种或者其顶点的值为无穷大为止
 * @author ypt
 */
public class DijkstraSearch extends AbstractSearch{

    /**
     * 这里构造一个有序队列 用于删除并返回优先级最低的索引
     */
    private PriorityQueue<Town> indexMinPQ;
    /**
     * 构造函数
     * @param graph 图
     * @param from  小镇
     */
    public DijkstraSearch(AbstractGraph graph, Town from){
        super(graph, from);
        Map<Town, Map<DirectedTrip, BigDecimal>> townsMap = graph.adjs;
        Set<Town> towns = townsMap.keySet();
        // 先将所有小镇的距离初始化为最大值
        for (Town town : towns) {
            this.distsTo.put(town, Constant.NoSuch_Max_Dist);
        }
        // 优先队列 这里用from到这些小镇的距离作为排序器
        // 返回a和b的到达from的距离
        this.indexMinPQ = new PriorityQueue<>(Comparator.comparing(this::distTo));
        // 优先设置从自身出发的点
        for (Map.Entry<DirectedTrip, BigDecimal> entry : graph.adj(from).entrySet()) {
            DirectedTrip trip = entry.getKey();
            super.updateTrip(trip, trip.getTo(), trip.getDistance());
            this.indexMinPQ.add(trip.getTo());
        }
        // Dijkstra算法核心是每次为最短路径树添加一条边
        while (!this.indexMinPQ.isEmpty()){
            super.relax(this.indexMinPQ.poll());
        }
    }

    /**
     * 覆写父类的updateTrip方法，因为需要单独维护优先队列indexMinPQ的距离
     *      这里距离是排列方式（Comparator）
     * @param trip      trip是当前最优路径的最后一条边
     * @param to        目标地
     * @param dist      最优距离
     */
    @Override
    protected void updateTrip(DirectedTrip trip, Town to, BigDecimal dist) {
        super.updateTrip(trip, to, dist);
        // 这里的处理方式类似于重新调整to的距离
        // 如果存在则移除重新调整堆，如果不存在则新增
        this.indexMinPQ.remove(to);
        this.indexMinPQ.add(to);
    }
}
