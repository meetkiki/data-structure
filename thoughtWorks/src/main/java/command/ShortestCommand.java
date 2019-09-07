package command;


import common.Constant;
import core.AbstractGraph;
import core.DijkstraSearch;
import entity.SearchCondition;
import entity.Town;
import entity.Trip;
import org.junit.Assert;

/**
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
     * 执行最短距离运算
     * @param obj      请求参数
     * @return
     */
    @Override
    public Object executeAlgorithmInteface(Object obj) {
        Assert.assertNotNull(obj);
        Assert.assertEquals(obj.getClass(), SearchCondition.class);
        SearchCondition searchCondition = (SearchCondition) obj;
        Town from = searchCondition.getFrom();
        Town to = searchCondition.getTo();
        DijkstraSearch search = new DijkstraSearch(graph, from);
        Trip trip = search.pathTo(to);
        return trip == null ? Constant.No_Such_Route : trip.sumDist();
    }
}
