package command;


import common.Constant;
import core.AbstractGraph;
import core.DijkstraSearch;
import entity.SearchCondition;
import entity.Town;
import entity.Trip;
import org.junit.Assert;

/**
 * @author ypt
 * 执行最短路径计算的命令
 */
public class ShortestCommand extends AbstractCommand implements Command {

    /**
     * 构造
     * @param graph     执行运算的图对象
     */
    public ShortestCommand(AbstractGraph graph) {
        super(graph);
    }

    /**
     * 采用Dijkstra搜索最短距离运算 (必须要保证传入的参数没有负权边)
     * @see DijkstraSearch 最短路径Dijkstra搜索算法
     * @param obj       请求参数SearchCondition {@link SearchCondition} 路线搜索条件
     *                  主要两个参数 from - > to
     * @return          如果不存在这样的路径，则输出“NO SUCH ROUTE” {@link Constant#No_Such_Route}
     *                  否则输出路线的距离
     */
    @Override
    public Object executeAlgorithmInteface(Object obj) {
        Assert.assertEquals(obj.getClass(), SearchCondition.class);
        SearchCondition searchCondition = (SearchCondition) obj;
        Town from = searchCondition.getFrom();
        Town to = searchCondition.getTo();
        DijkstraSearch search = new DijkstraSearch(graph, from);
        Trip trip = search.pathTo(to);
        return trip == null ? Constant.No_Such_Route : trip.sumDist();
    }
}
