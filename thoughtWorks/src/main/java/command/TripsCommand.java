package command;

import core.AbstractGraph;
import entity.SearchCondition;
import entity.Town;
import entity.Trip;
import org.junit.Assert;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author ypt
 * 执行路线搜索的命令
 */
public class TripsCommand extends AbstractCommand implements Command{

    /**
     * 构造
     * @param graph     执行运算的图对象
     */
    public TripsCommand(AbstractGraph graph) {
        super(graph);
    }

    /**
     * 使用深度优先搜索算法执行路线搜索运算
     * @see AbstractGraph#searchDFS(Trip, Town, Set, Predicate, Predicate)
     * @param obj       请求参数SearchCondition {@link SearchCondition} 路线搜索条件
     *                  主要四个参数 from - > to           起始到结束
     *                  stopCondition                    停止搜索的条件
     *                  returnCondition                  返回数据的条件
     * @return          返回路线的总数
     */
    @Override
    public Object executeAlgorithmInteface(Object obj) {
        Assert.assertEquals(obj.getClass(), SearchCondition.class);
        SearchCondition searchCondition = (SearchCondition) obj;
        Collection<Trip> trips = graph.routeTrips(searchCondition);
        return trips.size();
    }
}
