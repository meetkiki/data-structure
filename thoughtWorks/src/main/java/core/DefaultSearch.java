package core;

import entity.DirectedTrip;
import entity.Town;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author ypt
 * @ClassName DefaultSearch
 * @Description 默认实现搜索类 执行通用搜索
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
        Map<Town, LinkedList<DirectedTrip>> townsMap = graph.adjs;
        Set<Town> towns = townsMap.keySet();
        // 先将所有小镇的距离初始化为最大值
        for (Town town : towns) {
            this.distsTo.put(town,new BigDecimal(Integer.MAX_VALUE));
        }
        // 自身为0
        this.distsTo.put(from,new BigDecimal("0"));
        // 放松每一个顶点 直到不存在有效边为止
        for (Town town : towns) {
            this.relax(town);
        }
    }
}
