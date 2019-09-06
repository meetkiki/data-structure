package core;

import common.Constant;
import entity.DirectedTrip;
import entity.Town;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author ypt
 * @ClassName DefaultSearch
 * @Description 默认实现搜索类 执行通用搜索最短路径
 * @date 2019/9/6 17:05
 */
public class DefaultSearch extends AbstractSearch {
    
    
    /**
     * 构造函数
     * @param graph 图
     * @param from  小镇
     */
    public DefaultSearch(Digraph graph, Town from) {
        super(graph, from);
        Map<Town, Map<DirectedTrip,BigDecimal>> townsMap = graph.adjs;
        Set<Town> towns = townsMap.keySet();
        // 先将所有小镇的距离初始化为最大值
        for (Town town : towns) {
            this.distsTo.put(town, Constant.NoSuch_Dist);
        }
        // 自身为0
        this.distsTo.put(from, BigDecimal.ZERO);
        // 放松每一个顶点 直到不存在有效边为止
        // 这里没有指定放松的顺序,要证明这个算法是最短路径
        // 只需要证明它们都会放松所有边直到所有边都无法放松为止
        for (Town town : towns) {
            this.relax(town);
        }
    }
}
