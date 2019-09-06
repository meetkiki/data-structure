package core;

import common.Constant;
import entity.DirectedTrip;
import entity.Town;
import utils.IndexMinPQ;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * 最短路径的Dijkstra算法
 * @author tao
 */
public class DijkstraSearch extends AbstractSearch{

    /**
     * 索引优先队列
     */
    private IndexMinPQ<BigDecimal> indexMinPQ;
    /**
     * 构造函数
     * @param graph 图
     * @param from  小镇
     */
    public DijkstraSearch(Digraph graph, Town from) {
        super(graph, from);
        Map<Town, Map<DirectedTrip, BigDecimal>> townsMap = graph.adjs;
        Set<Town> towns = townsMap.keySet();
        // 先将所有小镇的距离初始化为最大值
        for (Town town : towns) {
            this.distsTo.put(town, Constant.NoSuch_Max_Dist);
        }
        // 自身为0
        this.distsTo.put(from, BigDecimal.ZERO);
        this.indexMinPQ = new IndexMinPQ<>(towns.size());
    }
}
